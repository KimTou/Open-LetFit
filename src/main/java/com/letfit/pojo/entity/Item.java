package com.letfit.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cjt
 * @date 2021/5/20 10:34
 */
@ApiModel("题目选项")
@Data
public class Item {

    @ApiModelProperty("选项序号")
    private char word;

    @ApiModelProperty("选项内容")
    private String content;

}
