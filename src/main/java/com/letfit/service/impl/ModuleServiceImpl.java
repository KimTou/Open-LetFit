package com.letfit.service.impl;

import com.letfit.aop.annotation.ReadOnly;
import com.letfit.mapper.ModuleMapper;
import com.letfit.pojo.entity.Module;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.vo.CircleInfo;
import com.letfit.pojo.vo.RecordInfo;
import com.letfit.pojo.vo.UserRecordModule;
import com.letfit.service.ModuleService;
import com.letfit.common.CodeEnum;
import com.letfit.util.RedisUtil;
import com.letfit.util.ValiDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjt
 * @date 2021/4/10 15:42
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 打卡记录点赞数
     */
    private static final String RECORD_TOTAL_LIKE_COUNT = "record_total_like_count:";

    /**
     * 所有模块
     */
    private static final String ALL_MODULE = "all_module";

    /**
     * 查询所有模块
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<List<Module>> getModuleList() {
        List<Module> moduleList = (List<Module>) redisUtil.get(ALL_MODULE);
        if(moduleList == null || moduleList.size() == 0){
            moduleList = moduleMapper.getModuleList();
            redisUtil.set(ALL_MODULE, moduleList);
        }
        return ResultInfo.success(CodeEnum.SUCCESS,moduleList);
    }

    /**
     * 根据用户id查询用户加入的圈子
     * @param userId
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<List<UserRecordModule>> getUserModule(Integer userId) {
        if(!ValiDateUtil.isLegalString(String.valueOf(userId))){
            return ResultInfo.error(CodeEnum.NULL_PARAM);
        }
        return ResultInfo.success(CodeEnum.SUCCESS, moduleMapper.getUserModule(userId));
    }

    /**
     * 拿到圈子打卡记录信息（每页4个）
     * @param moduleId
     * @param currentPage
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<CircleInfo> getCircleInfo(Integer moduleId, Integer currentPage) {
        int rows = 4;
        int start = (currentPage - 1) * rows;
        Module module = moduleMapper.getModuleByModuleId(moduleId);
        if(module == null){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL);
        }
        CircleInfo circleInfo = new CircleInfo();
        circleInfo.setModule(module);
        circleInfo.setCurrentPage(currentPage);
        List<RecordInfo> recordInfoList = moduleMapper.getCirclePage(moduleId, start, rows);
        //若总点赞数有变化，需修改
        for(int i = 0; i < recordInfoList.size(); i++){
            RecordInfo recordInfo = recordInfoList.get(i);
            Integer totalLike = (Integer) redisUtil.get(RECORD_TOTAL_LIKE_COUNT + recordInfo.getRecordId().toString());
            //为空则代表总点赞数没有变化
            if(totalLike != null){
                recordInfo.getRecord().setTotalLike(totalLike);
                recordInfoList.set(i, recordInfo);
            }
        }
        //不展示用户是否打过卡
        circleInfo.setRecordInfoList(recordInfoList);
        return ResultInfo.success(CodeEnum.SUCCESS, circleInfo);
    }

}
