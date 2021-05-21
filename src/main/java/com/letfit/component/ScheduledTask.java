package com.letfit.component;

import com.letfit.mapper.RecordMapper;
import com.letfit.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author cjt
 * @date 2021/4/14 21:04
 */
@Component
public class ScheduledTask {

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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RecordMapper recordMapper;

    /**
     * 每一小时将点赞数据从redis同步到 mysql
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void toMysql(){
        likeUser();
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
            if(likeMap.size() != 0){
                //点赞者入库
                recordMapper.likeUserToMysql(Integer.valueOf(recordId), likeMap);
            }
            //先入数据库，再清缓存
            redisUtil.del(NEW_USER + recordId);
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
