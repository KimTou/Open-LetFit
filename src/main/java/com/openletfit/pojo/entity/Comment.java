package com.openletfit.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cjt
 * @date 2021/4/30 19:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论实体")
public class Comment implements Serializable {

    @Id
    @ApiModelProperty("评论id")
    private Long commentId;

    @NotNull(message = "一级父评论id不能为空")
    @ApiModelProperty("一级父评论id")
    private Long pid;

    @NotNull(message = "圈子id不能为空")
    @ApiModelProperty("圈子id")
    private Integer recordId;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("被回复的用户的name")
    private String repliedUserName;

    @NotBlank(message = "内容不能为空")
    @Size(max = 100)
    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论发表时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
