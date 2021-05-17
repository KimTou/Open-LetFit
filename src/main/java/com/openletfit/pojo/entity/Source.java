package com.openletfit.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author cjt
 * @date 2021/4/9 21:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("模块资源")
public class Source implements Serializable {

    @ApiModelProperty("资源id")
    private Integer sourceId;

    @Size(max = 50)
    @ApiModelProperty("资源标题")
    private String sourceTitle;

    @ApiModelProperty("资源路径")
    private String sourcePath;

    @ApiModelProperty("资源封面")
    private String sourceCover;

    @ApiModelProperty("资源所在模块id")
    private Integer moduleId;

}
