package com.openletfit.service.impl;

import com.openletfit.aop.annotation.ReadOnly;
import com.openletfit.mapper.AdminMapper;
import com.openletfit.mapper.ModuleMapper;
import com.openletfit.pojo.entity.Admin;
import com.openletfit.pojo.entity.Module;
import com.openletfit.pojo.dto.ResultInfo;
import com.openletfit.pojo.entity.Source;
import com.openletfit.service.AdminService;
import com.openletfit.shiro.auth.BcryptUtil;
import com.openletfit.common.CodeEnum;
import com.openletfit.util.FileUtil;
import com.openletfit.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.util.List;

/**
 * @author cjt
 * @date 2021/4/10 11:00
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 所有模块
     */
    private static final String ALL_MODULE = "all_module";

    /**
     * 添加模块
     * @param name 模块名称
     * @param file 模块图片
     * @param suffixName 图片后缀名
     * @return
     */
    @Override
    public ResultInfo<Module> addModule(String name, BufferedInputStream file, String suffixName) {
        String moduleCover = FileUtil.uploadImgQny(file,suffixName);
        Module module = new Module();
        module.setModuleName(name);
        module.setModuleCover(moduleCover);
        adminMapper.insertModule(module);
        List<Module> moduleList = moduleMapper.getModuleList();
        redisUtil.set(ALL_MODULE, moduleList);
        return ResultInfo.success(CodeEnum.SUCCESS, module);
    }

    /**
     * 上传资源
     * @param moduleName
     * @param sourceTitle
     * @param file
     * @param suffixName
     * @return
     */
    @Override
    public ResultInfo<Source> addSource(String moduleName, String sourceTitle, BufferedInputStream file, String suffixName) {
        Module module = adminMapper.findModuleByName(moduleName);
        if(module == null){
            return ResultInfo.error(10003,"模块名有误");
        }
        Source source = new Source();
        source.setModuleId(module.getModuleId());
        source.setSourceTitle(sourceTitle);
        source.setSourcePath(FileUtil.uploadVideo(file, suffixName));
        adminMapper.uploadSource(source);
        return ResultInfo.success(CodeEnum.SUCCESS, source);
    }

    /**
     * 上传视频封面
     * @param sourceId
     * @param file
     * @param suffixName
     * @return
     */
    @Override
    public ResultInfo<Source> uploadSourceCover(Integer sourceId, BufferedInputStream file, String suffixName) {
        String sourceCover = FileUtil.uploadImgQny(file, suffixName);
        adminMapper.uploadSourceCover(sourceId, sourceCover);
        return ResultInfo.success(CodeEnum.SUCCESS);
    }

    /**
     * 新增管理员
     * @param admin
     */
    @Override
    public void insertAdmin(Admin admin) {
        //加密存储
        admin.setPassword(BcryptUtil.encode(admin.getPassword()));
        adminMapper.insertAdmin(admin);
    }

    @ReadOnly
    @Override
    public Admin findAdminByName(String adminName) {
        return adminMapper.findAdminByName(adminName);
    }


}
