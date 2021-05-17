package com.openletfit.service.impl;

import com.openletfit.aop.annotation.NullCheck;
import com.openletfit.aop.annotation.ReadOnly;
import com.openletfit.mapper.RecordMapper;
import com.openletfit.mapper.UserMapper;
import com.openletfit.pojo.entity.Comment;
import com.openletfit.pojo.entity.Like;
import com.openletfit.pojo.entity.Record;
import com.openletfit.pojo.dto.ResultInfo;
import com.openletfit.pojo.entity.User;
import com.openletfit.pojo.vo.CommentInfo;
import com.openletfit.pojo.vo.RecordInfo;
import com.openletfit.pojo.vo.UserComment;
import com.openletfit.service.RecordService;
import com.openletfit.common.CodeEnum;
import com.openletfit.util.FileUtil;
import com.openletfit.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author cjt
 * @date 2021/4/18 23:18
 */
@Service
@Slf4j
@Validated
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

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

    /**
     * 点赞打卡
     * @param userId 点赞者id
     * @param recordId 打卡记录id
     * @return
     */
    @Override
    public ResultInfo<Integer> likeRecord(@NotNull String userId, @NotNull String recordId) {
        //参数校验
        if(userMapper.findUserByUserId(Integer.valueOf(userId)) == null ||
                recordMapper.findRecordByRecordId(Integer.valueOf(recordId)) == null){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL);
        }
        //同步加锁
        synchronized (this) {
            Set<Object> userIdSet = redisUtil.zGet(RECORD_USER_LIST + recordId);
            System.out.println(userIdSet);
            //获取打卡记录的点赞者集合
            if (userIdSet.size() == 0) {
                //获取点赞者集合（已按时间排好序）
                List<Like> likeList = recordMapper.selectRecordTotalLike(Integer.valueOf(recordId));
                //该打卡记录的总点赞数进redis
                redisUtil.set(RECORD_TOTAL_LIKE_COUNT + recordId, likeList.size());
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
            //返回点赞数
            return ResultInfo.success(CodeEnum.SUCCESS, userIdSet.size());
        }
    }

    /**
     * 发布打卡
     * @param moduleId
     * @param userId
     * @param recordContent
     * @param file
     * @param suffixName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultInfo<Record> putRecord(Integer moduleId, Integer userId, String recordContent, BufferedInputStream file, String suffixName) {
        //上传图片
        String recordImg = FileUtil.uploadImgQny(file,suffixName);
        Record record = Record.builder()
                .moduleId(moduleId)
                .userId(userId)
                .recordContent(recordContent)
                .recordImg(recordImg)
                .totalLike(0)
                .gmtCreate(LocalDateTime.now())
                .build();
        //返回封装有自增id的record
        recordMapper.putRecord(record);
        return ResultInfo.success(CodeEnum.SUCCESS,record);
    }

    /**
     * 发布打卡（无图片）
     * @param moduleId
     * @param userId
     * @param recordContent
     * @return
     */
    @Override
    public ResultInfo<Record> putRecordWithoutImg(Integer moduleId, Integer userId, String recordContent) {
        Record record = Record.builder()
                .moduleId(moduleId)
                .userId(userId)
                .recordContent(recordContent)
                .recordImg(null)
                .totalLike(0)
                .gmtCreate(LocalDateTime.now())
                .build();
        //返回封装有自增id的record
        recordMapper.putRecord(record);
        return ResultInfo.success(CodeEnum.SUCCESS,record);
    }

    /**
     * 通过用户id查找打卡记录（每页6个）
     * @param userId
     * @param moduleId
     * @param currentPage
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<List<Record>> getRecordListByUserId(Integer userId,Integer moduleId, Integer currentPage) {
        //参数校验
        if(userId <= 0 || currentPage <= 0){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL);
        }
        int rows = 6;
        int start = (currentPage - 1) * rows;
        List<Record> recordList = recordMapper.getRecordListByUserId(userId, moduleId, start, rows);
        //若总点赞数有变化，需修改
        for(int i = 0; i < recordList.size(); i++){
            Record record = recordList.get(i);
            Integer totalLike = (Integer) redisUtil.get(RECORD_TOTAL_LIKE_COUNT + record.getRecordId().toString());
            //为空则代表总点赞数没有变化
            if(totalLike != null){
                record.setTotalLike(totalLike);
                recordList.set(i, record);
            }
        }
        return ResultInfo.success(CodeEnum.SUCCESS, recordList);
    }

    /**
     * 发表评论
     * @param comment
     * @return
     */
    @Override
    public ResultInfo<?> insertComment(Comment comment) {
        //设置发表时间
        comment.setGmtCreate(LocalDateTime.now());
        recordMapper.insertComment(comment);
        return ResultInfo.success(CodeEnum.SUCCESS);
    }

    /**
     * 获取打卡记录详细信息
     * @param recordId
     * @param userId
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<RecordInfo> getRecordInfo(@NullCheck Integer recordId, @NullCheck String userId) {
        Record record = recordMapper.getRecordById(recordId);
        if(record == null){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL, null);
        }
        //若总点赞数有变化，需修改
        Integer totalLike = (Integer) redisUtil.get(RECORD_TOTAL_LIKE_COUNT + record.getRecordId().toString());
        if(totalLike != null){
            record.setTotalLike(totalLike);
        }
        RecordInfo recordInfo = new RecordInfo();
        //设置打卡记录作者信息
        User user = userMapper.findUserByUserId(record.getUserId());
        if(user != null){
            recordInfo.setUserName(user.getUserName());
            recordInfo.setUserAvatar(user.getUserAvatar());
        }

        //判断是否点过赞
        Set<Object> userIdSet = redisUtil.zGet(RECORD_USER_LIST + recordId);
        //获取打卡记录的点赞者集合
        if (userIdSet.size() == 0) {
            //获取点赞者集合（已按时间排好序）
            List<Like> likeList = recordMapper.selectRecordTotalLike(recordId);
            //该打卡记录的总点赞数进redis
            redisUtil.set(RECORD_TOTAL_LIKE_COUNT + recordId, likeList.size());
            for (Like like : likeList) {
                redisUtil.zadd(RECORD_USER_LIST + recordId, like.getLikerId().toString(), like.getGmtCreate());
            }
        }else{
            //该打卡记录的总点赞数进redis
            redisUtil.set(RECORD_TOTAL_LIKE_COUNT + recordId, userIdSet.size());
        }
        //设置是否点过赞
        if(userIdSet.contains(userId)){
            recordInfo.setIsLike(1);
        }else{
            recordInfo.setIsLike(0);
        }

        List<CommentInfo> comment = new LinkedList<>();
        //获取父评论集合
        List<UserComment> parentComment = recordMapper.getCommentList(recordId, 0L);
        for(UserComment userComment : parentComment){
            Long pid = userComment.getCommentId();
            //获取父评论对应的子评论集合
            List<UserComment> childComment = recordMapper.getCommentList(recordId, pid);
            CommentInfo commentInfo = new CommentInfo();
            commentInfo.setParentComment(userComment);
            commentInfo.setChildComment(childComment);
            comment.add(commentInfo);
        }
        recordInfo.setRecord(record);
        recordInfo.setCommentInfo(comment);
        log.info("打卡记录详细信息:{}", recordInfo);
        return ResultInfo.success(CodeEnum.SUCCESS, recordInfo);
    }

}
