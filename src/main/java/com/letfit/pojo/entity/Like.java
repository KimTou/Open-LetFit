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
 * @date 2021/4/14 17:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("点赞记录")
public class Like implements Serializable {

    private static final long serialVersionUID = -6950565660840876288L;

    private Integer id;

    @ApiModelProperty("被点赞打卡记录id")
    private Integer recordId;

    @ApiModelProperty("点赞者id")
    private Integer likerId;

    @ApiModelProperty("点赞时间")
    private Long gmtCreate;

}
