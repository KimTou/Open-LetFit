package com.openletfit.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.openletfit.pojo.entity.Record;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/4 10:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("打卡记录的详细信息")
public class RecordInfo implements Serializable {

    @ApiModelProperty("唯一标识")
    private Integer recordId;

    @ApiModelProperty("打卡记录发布者信息")
    private String userName;

    @ApiModelProperty("打卡记录发布者头像")
    private String userAvatar;

    @ApiModelProperty("打卡记录")
    private Record record;

    @ApiModelProperty("是否点赞")
    private Integer isLike;

    @ApiModelProperty("评论Map集合（ key为父评论，value为子评论 ）")
    private List<CommentInfo> commentInfo;

}
