package com.letfit.service.impl;

import com.letfit.aop.annotation.ReadOnly;
import com.letfit.mapper.ModuleMapper;
import com.letfit.mapper.SourceMapper;
import com.letfit.mapper.UserMapper;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Module;
import com.letfit.pojo.entity.Source;
import com.letfit.pojo.vo.IndexInfo;
import com.letfit.service.SourceService;
import com.letfit.common.CodeEnum;
import com.letfit.util.ValiDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/7 22:23
 */
@Service
public class SourceServiceImpl implements SourceService {

    private final ModuleMapper moduleMapper;

    private final SourceMapper sourceMapper;

    private final UserMapper userMapper;

    @Autowired
    public SourceServiceImpl(ModuleMapper moduleMapper, SourceMapper sourceMapper, UserMapper userMapper){
        this.sourceMapper = sourceMapper;
        this.moduleMapper = moduleMapper;
        this.userMapper = userMapper;
    }

    /**
     * 获取首页信息
     * @param userId
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<IndexInfo> getIndexInfo(Integer userId) {
        //参数校验
        if(userId < 1 || userMapper.findUserByUserId(userId)==null){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL);
        }
        //获取用户最近打卡的圈子集合
        List<Module> moduleList = moduleMapper.getUserRecentModule(userId);
        List<Source> sourceList;
        if(moduleList.size()==0){
            //默认推荐
            sourceList = sourceMapper.getDefaultSource();
        }else{
            //圈子id
            List<Integer> moduleIdList = new ArrayList<>();
            for(Module module : moduleList){
                moduleIdList.add(module.getModuleId());
            }
            //获取首页推荐视频
            sourceList = sourceMapper.getIndexSource(moduleIdList);
        }
        IndexInfo indexInfo = IndexInfo.builder().moduleList(moduleList).sourceList(sourceList).build();
        return ResultInfo.success(CodeEnum.SUCCESS, indexInfo);
    }

    /**
     * 获取模块对应的资源
     * @param moduleId
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<List<Source>> getSourceByModuleId(Integer moduleId) {
        if(moduleId < 1){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL);
        }
        List<Source> sourceList = sourceMapper.getSourceByModuleId(moduleId);
        return ResultInfo.success(CodeEnum.SUCCESS, sourceList);
    }

    /**
     * 根据标题关键字模糊查询资源
     * @param title
     * @return
     */
    @ReadOnly
    @Override
    public ResultInfo<List<Source>> searchSource(String title) {
        if(!ValiDateUtil.isLegalString(title)){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL, null);
        }
        List<Source> sourceList = sourceMapper.searchSource(title);
        return ResultInfo.success(CodeEnum.SUCCESS, sourceList);
    }

}
