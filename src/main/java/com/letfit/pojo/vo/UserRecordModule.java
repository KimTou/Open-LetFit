package com.letfit.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cjt
 * @date 2021/5/5 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户打卡记录的模块")
public class UserRecordModule {

    @ApiModelProperty("模块id")
    private Integer moduleId;

    @ApiModelProperty("模块名")
    private String moduleName;

    @ApiModelProperty("模块封面")
    private String moduleCover;

    @ApiModelProperty("模块的打卡记录数量")
    private Integer recordNumber;

}
