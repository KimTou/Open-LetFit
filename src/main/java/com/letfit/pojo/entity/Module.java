package com.letfit.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cjt
 * @date 2021/4/9 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("运动模块")
public class Module implements Serializable {

    private static final long serialVersionUID = 5433900920186568671L;

    @ApiModelProperty("模块id")
    private Integer moduleId;

    @ApiModelProperty("模块名")
    private String moduleName;

    @ApiModelProperty("模块封面")
    private String moduleCover;

}
