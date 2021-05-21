package com.letfit.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.letfit.pojo.entity.Module;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjt
 * @date 2021/4/19 22:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("圈子信息")
public class CircleInfo implements Serializable {

    private static final long serialVersionUID = -2164588121641185016L;

    @ApiModelProperty("圈子对应模块")
    private Module module;

    @ApiModelProperty("当前页码")
    private Integer currentPage;

    @ApiModelProperty("圈子打卡记录")
    private List<RecordInfo> recordInfoList;

}
