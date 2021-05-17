package com.openletfit;

import com.openletfit.mapper.ModuleMapper;
import com.openletfit.mapper.RecordMapper;
import com.openletfit.pojo.entity.Like;
import com.openletfit.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cjt
 * @date 2021/5/15 22:12
 */
@SpringBootTest
public class LikeTest {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 打卡记录点赞数
     */
    private static final String RECORD_TOTAL_LIKE_COUNT = "record_total_like_count:";

    /**
     * 打卡记录点赞者id
     */
    private static final String RECORD_USER_LIST = "record_user_list:";

    /**
     * 未入库的用户列表
     */
    private static final String NEW_USER = "new_user:";

    /**
     * 未入库的用户列表的打卡记录id列表
     */
    private static final String NEW_USER_RECORD = "new_user_record";

    /**
     * 取消点赞的用户列表
     */
    private static final String DELETE_USER = "delete_user:";

    /**
     * 需要删除的用户列表的打卡记录id列表
     */
    private static final String DELETE_USER_RECORD = "delete_user_record";

    @Test
    void printZSet(){
        String recordId = "4";
        Set<Object> objects = redisUtil.zGet(RECORD_USER_LIST + recordId);
        System.out.println(objects);
    }

    @Test
    void likeRecord() {
        String recordId = "3";
        String userId = "3";
        List<Like> likeList;
        Set<Object> userIdSet = redisUtil.zGet(RECORD_USER_LIST + recordId);
        System.out.println(userIdSet);
        //获取打卡记录的点赞者集合
        if (userIdSet.size() == 0) {
            //获取点赞者集合（已按时间排好序）
            likeList = recordMapper.selectRecordTotalLike(Integer.valueOf(recordId));
            //该打卡记录的总点赞数进redis
            redisUtil.set(RECORD_TOTAL_LIKE_COUNT + recordId, likeList.size());
            System.out.println(likeList);
            for (Like like : likeList) {
                redisUtil.zadd(RECORD_USER_LIST + recordId, like.getLikerId().toString(), like.getGmtCreate());
            }
        }else{
            //该打卡记录的总点赞数进redis
            redisUtil.set(RECORD_TOTAL_LIKE_COUNT + recordId, userIdSet.size());
        }
        //判断是否点过赞，存在则为取消点赞
        if (userIdSet.contains(userId)) {
            redisUtil.zRemove(RECORD_USER_LIST + recordId, userId);
            //若取消点赞的是未落库的用户，需要将其从 new_user 中移除
            if (redisUtil.hget(NEW_USER + recordId, userId) != null) {
                redisUtil.hdel(NEW_USER + recordId, userId);
            } else {
                //加入待处理打卡记录队列
                redisUtil.sSet(DELETE_USER_RECORD, recordId);
                //若取消点赞的是已落库的用户，需要加入删除队列
                redisUtil.hset(DELETE_USER + recordId, userId, System.currentTimeMillis());
            }
            //总点赞数-1
            redisUtil.decr(RECORD_TOTAL_LIKE_COUNT + recordId, 1);
        } else {
            //成为点赞者
            redisUtil.zadd(RECORD_USER_LIST + recordId, userId, System.currentTimeMillis());
            //若是已落库的用户并被加入删除队列，需将其从删除队列中移除
            if (redisUtil.hget(DELETE_USER + recordId, userId) != null) {
                redisUtil.hdel(DELETE_USER + recordId, userId);
            } else {
                //加入待处理打卡记录队列
                redisUtil.sSet(NEW_USER_RECORD, recordId);
                //未落库，则加入待入库点赞者队列
                redisUtil.hset(NEW_USER + recordId, userId, System.currentTimeMillis());
            }
            redisUtil.incr(RECORD_TOTAL_LIKE_COUNT + recordId, 1);
        }
    }

    /**
     * 测试打卡点赞
     */
    @Test
    void testLike(){
        unLikeUser();

    }

    /**
     * 处理新增点赞
     */
    void likeUser(){
        //获取需要处理的record集合
        Set<Object> newUserRecord = redisUtil.sGet(NEW_USER_RECORD);
        for(Object recordIdObj : newUserRecord){
            String recordId = (String) recordIdObj;
            //获取record对应的点赞者信息
            Map<Object, Object> newUserMap = redisUtil.hmget(NEW_USER + recordId);
            Map<Integer, Long> likeMap = new HashMap<>(newUserMap.size());
            for(Map.Entry<Object, Object> entry : newUserMap.entrySet()){
                String likerIdStr = entry.getKey().toString();
                Integer likerId = Integer.valueOf(likerIdStr);
                Long gmtCreate = (Long) entry.getValue();
                likeMap.put(likerId, gmtCreate);
            }
            redisUtil.del(NEW_USER + recordId);
            if(likeMap.size() != 0){
                //点赞者入库
                recordMapper.likeUserToMysql(Integer.valueOf(recordId), likeMap);
            }
            Integer totalLike = (Integer) redisUtil.get(RECORD_TOTAL_LIKE_COUNT + recordId);
            if(totalLike != null){
                recordMapper.updateTotalLike(Integer.valueOf(recordId), totalLike);
                redisUtil.del(RECORD_TOTAL_LIKE_COUNT + recordId);
            }
            redisUtil.setRemove(NEW_USER_RECORD, recordId);
        }
    }

    /**
     * 处理新增删除点赞
     */
    void unLikeUser(){
        Set<Object> deleteUserRecord = redisUtil.sGet(DELETE_USER_RECORD);
        for(Object recordIdObj : deleteUserRecord){
            String recordId = (String) recordIdObj;
            Map<Object, Object> unLikeMap = redisUtil.hmget(DELETE_USER + recordId);
            Set<Object> unLikerId = unLikeMap.keySet();
            redisUtil.del(DELETE_USER + recordId);
            if(unLikerId.size() != 0){
                recordMapper.deleteLike(Integer.valueOf(recordId), unLikerId);
            }
            Integer totalLike = (Integer) redisUtil.get(RECORD_TOTAL_LIKE_COUNT + recordId);
            //防止处理新增点赞时已更新，需判空
            if(totalLike != null){
                recordMapper.updateTotalLike(Integer.valueOf(recordId), totalLike);
                redisUtil.del(RECORD_TOTAL_LIKE_COUNT + recordId);
            }
            redisUtil.setRemove(DELETE_USER_RECORD, recordId);
        }
    }


}
