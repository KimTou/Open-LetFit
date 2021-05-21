package com.letfit.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cjt
 * @date 2021/4/8 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体")
public class User implements Serializable {

    private static final long serialVersionUID = 6455263795232499810L;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户openid")
    private String openId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像url")
    private String userAvatar;

    @ApiModelProperty("用户积分")
    private Integer userPoint;

    @ApiModelProperty("用户被点赞总数")
    private Integer totalLike;

}
