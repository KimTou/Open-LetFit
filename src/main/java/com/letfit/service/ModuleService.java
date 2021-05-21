package com.letfit.service;

import com.letfit.pojo.entity.Module;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.vo.CircleInfo;
import com.letfit.pojo.vo.UserRecordModule;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjt
 * @date 2021/4/10 15:41
 */
@Service
public interface ModuleService {

    /**
     * 查询所有模块
     * @return
     */
    ResultInfo<List<Module>> getModuleList();

    /**
     * 根据用户id查询用户加入的圈子
     * @param userId
     * @return
     */
    ResultInfo<List<UserRecordModule>> getUserModule(Integer userId);

    /**
     * 拿到圈子打卡记录信息（每页4个）
     * @param moduleId
     * @param currentPage
     * @return
     */
    ResultInfo<CircleInfo> getCircleInfo(Integer moduleId, Integer currentPage);

}
