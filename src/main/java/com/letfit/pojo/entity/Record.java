package com.letfit.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cjt
 * @date 2021/4/13 21:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("打卡记录")
public class Record implements Serializable {

    private static final long serialVersionUID = 7402047345945892291L;

    @ApiModelProperty("打卡记录id")
    private Integer recordId;

    @NotNull(message = "模块id（moduleId） 不能为空")
    @ApiModelProperty("模块id")
    private Integer moduleId;

    @NotNull(message = "打卡用户id（userId） 不能为空")
    @ApiModelProperty("打卡用户id")
    private Integer userId;

    @Size(max = 250)
    @ApiModelProperty("打卡文本")
    private String recordContent;

    @ApiModelProperty("打卡图片")
    private String recordImg;

    @ApiModelProperty("打卡记录总点赞数")
    private Integer totalLike;

    @ApiModelProperty("打卡记录创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
