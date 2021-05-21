package com.letfit.pojo.vo;

import com.letfit.pojo.entity.Comment;
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

    private static final long serialVersionUID = -8254728517446871935L;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("评论者名")
    private String userName;

    @ApiModelProperty("评论者头像")
    private String avatar;

    @ApiModelProperty("评论信息")
    private Comment comment;

}
