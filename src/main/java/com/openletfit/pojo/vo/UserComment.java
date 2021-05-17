package com.openletfit.pojo.vo;

import com.openletfit.pojo.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cjt
 * @date 2021/5/4 11:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论详细信息")
public class UserComment implements Serializable {

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("评论者名")
    private String userName;

    @ApiModelProperty("评论者头像")
    private String avatar;

    @ApiModelProperty("评论信息")
    private Comment comment;

}
