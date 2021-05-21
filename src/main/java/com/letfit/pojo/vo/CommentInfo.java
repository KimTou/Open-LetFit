package com.letfit.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/10 23:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("打卡记录的评论区")
public class CommentInfo implements Serializable {

    private static final long serialVersionUID = -1364419707789071144L;

    @ApiModelProperty("父评论")
    private UserComment parentComment;

    @ApiModelProperty("父评论对应的子评论集合")
    private List<UserComment> childComment;

}
