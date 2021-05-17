package com.openletfit.controller;

import com.openletfit.pojo.dto.ResultInfo;
import com.openletfit.pojo.entity.Source;
import com.openletfit.pojo.vo.IndexInfo;
import com.openletfit.service.SourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/7 23:08
 */
@Api(tags = "资源接口")
@RestController
@RequestMapping("/source")
public class SourceController {

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService){
        this.sourceService = sourceService;
    }

    @ApiOperation("获取首页信息")
    @GetMapping("/getIndexInfo/{userId}")
    public ResultInfo<IndexInfo> getIndexInfo(@PathVariable("userId") Integer userId){
        return sourceService.getIndexInfo(userId);
    }

    @ApiOperation("获取模块对应的资源")
    @GetMapping("/getSourceByModuleId/{moduleId}")
    public ResultInfo<List<Source>> getSourceByModuleId(@PathVariable("moduleId") Integer moduleId){
        return sourceService.getSourceByModuleId(moduleId);
    }

    @ApiOperation("模糊查询资源")
    @GetMapping("/searchSource/{title}")
    public ResultInfo<List<Source>> searchSource(@PathVariable("title") String title){
        return sourceService.searchSource(title);
    }

}
