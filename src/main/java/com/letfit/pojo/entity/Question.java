package com.letfit.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/20 10:15
 */
@ApiModel("题目")
@Data
@Document(collection = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 3150463327892284399L;

    @ApiModelProperty("题目id")
    @Id
    private String id;

    @ApiModelProperty("题目描述")
    private String questionTitle;

    @ApiModelProperty("题目选项集合")
    private List<Item> items;

    @ApiModelProperty("题目答案")
    private char answer;

    @ApiModelProperty("题目解析")
    private String analyze;

    @ApiModelProperty("题目模块名")
    private String moduleName;

    @ApiModelProperty("图片路径")
    private String imagePath;

}
