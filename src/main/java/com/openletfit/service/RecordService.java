package com.openletfit.service;

import com.openletfit.pojo.entity.Comment;
import com.openletfit.pojo.entity.Record;
import com.openletfit.pojo.dto.ResultInfo;
import com.openletfit.pojo.vo.RecordInfo;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.util.List;

/**
 * @author cjt
 * @date 2021/4/18 23:18
 */
@Service
public interface RecordService {

    /**
     * 点赞打卡
     * @param userId 点赞者id
     * @param recordId 打卡记录id
     * @return
     */
    ResultInfo<Integer> likeRecord(@NotNull String userId, @NotNull String recordId);

    /**
     * 发布打卡
     * @param moduleId
     * @param userId
     * @param recordContent
     * @param file
     * @param suffixName
     * @return
     */
    ResultInfo<Record> putRecord(Integer moduleId, Integer userId,
                                 String recordContent, BufferedInputStream file, String suffixName);

    /**
     * 发布打卡（不带照片）
     * @param moduleId
     * @param userId
     * @param recordContent
     * @return
     */
    ResultInfo<Record> putRecordWithoutImg(Integer moduleId, Integer userId,
                                           String recordContent);

    /**
     * 通过用户id查找打卡记录
     * @param userId
     * @param moduleId
     * @param currentPage
     * @return
     */
    ResultInfo<List<Record>> getRecordListByUserId(Integer userId,Integer moduleId, Integer currentPage);

    /**
     * 发表评论
     * @param comment
     * @return
     */
    ResultInfo<?> insertComment(Comment comment);

    /**
     * 获取打卡记录详细信息
     * @param recordId
     * @param userId
     * @return
     */
    ResultInfo<RecordInfo> getRecordInfo(Integer recordId, String userId);

}
