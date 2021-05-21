package com.letfit.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cjt
 * @date 2021/5/20 23:16
 */
@ApiModel("文章")
@Document(collection = "article")
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = -6165424690276734141L;

    @Id
    @ApiModelProperty("文章id")
    private String id;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("文章信息")
    private String articleMsg;

    @ApiModelProperty("封面")
    private String articleCover;

    @ApiModelProperty("文章内容")
    private String articleContent;

    @ApiModelProperty("模块名")
    private String moduleName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
