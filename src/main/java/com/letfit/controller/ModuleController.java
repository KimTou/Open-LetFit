package com.letfit.controller;

import com.letfit.pojo.entity.Module;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.vo.CircleInfo;
import com.letfit.pojo.vo.UserRecordModule;
import com.letfit.service.ModuleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cjt
 * @date 2021/4/18 23:13
 */
@Api(tags = "模块管理接口")
@Validated
@RestController
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 查询所有模块
     * @return
     */
    @GetMapping("/getModuleList")
    public ResultInfo<List<Module>> getModuleList(){
        return moduleService.getModuleList();
    }

    /**
     * 查询用户圈子
     * @param userId
     * @return
     */
    @GetMapping("/getUserModule/{userId}")
    public ResultInfo<List<UserRecordModule>> getUserModule(@PathVariable("userId") Integer userId){
        return moduleService.getUserModule(userId);
    }

    /**
     * 拿到圈子打卡记录信息（每页4个）
     * @param moduleId
     * @param currentPage
     * @return
     */
    @GetMapping("/getCircleInfo/{moduleId}/{currentPage}")
    public ResultInfo<CircleInfo> getCircleInfo(@PathVariable("moduleId") Integer moduleId,
                                                @PathVariable("currentPage") Integer currentPage){
        return moduleService.getCircleInfo(moduleId, currentPage);
    }

}
