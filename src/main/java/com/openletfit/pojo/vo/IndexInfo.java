package com.openletfit.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.openletfit.pojo.entity.Module;
import com.openletfit.pojo.entity.Source;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/7 22:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("首页信息")
public class IndexInfo implements Serializable {

    @ApiModelProperty("用户最近打卡模块")
    private List<Module> moduleList;

    @ApiModelProperty("首页视频推荐")
    private List<Source> sourceList;

}
