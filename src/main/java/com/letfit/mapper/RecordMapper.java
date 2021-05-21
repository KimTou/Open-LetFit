package com.letfit.mapper;

import com.letfit.pojo.entity.Comment;
import com.letfit.pojo.entity.Like;
import com.letfit.pojo.entity.Record;
import com.letfit.pojo.vo.UserComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cjt
 * @date 2021/4/18 23:15
 */
@Repository
public interface RecordMapper {

    /**
     * 判断打卡记录是否存在
     * @param recordId
     * @return
     */
    Record findRecordByRecordId(Integer recordId);

    /**
     * 查询打卡记录点赞数（返回点赞的用户id集合）
     * @param recordId
     * @return
     */
    List<Like> selectRecordTotalLike(Integer recordId);

    /**
     * 将点赞者id集合同步至数据库
     * @param recordId
     * @param likeMap
     */
    void likeUserToMysql(Integer recordId, @Param("likeMap") Map<Integer, Long> likeMap);

    /**
     * 删除
     * @param recordId
     * @param unLikerIdSet
     */
    void deleteLike(Integer recordId, Set<Object> unLikerIdSet);

    /**
     * 更新点赞数
     * @param recordId
     * @param totalLike
     */
    void updateTotalLike(Integer recordId, Integer totalLike);

    /**
     * 发布打卡
     * @param record
     * @return
     */
    Integer putRecord(Record record);

    /**
     * 通过recordId获取打卡记录
     * @param recordId
     * @return
     */
    Record getRecordById(Integer recordId);

    /**
     * 通过用户id查找打卡记录
     * @param userId
     * @param moduleId
     * @param start
     * @param rows
     * @return
     */
    List<Record> getRecordListByUserId(Integer userId,Integer moduleId, int start, int rows);

    /**
     * 发表评论
     * @param comment
     */
    void insertComment(Comment comment);

    /**
     * 获取父子评论集合
     * @param recordId
     * @param pid
     * @return
     */
    List<UserComment> getCommentList(Integer recordId, Long pid);


}
