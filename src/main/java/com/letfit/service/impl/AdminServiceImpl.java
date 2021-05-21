package com.letfit.service.impl;

import com.letfit.aop.annotation.ReadOnly;
import com.letfit.mapper.AdminMapper;
import com.letfit.mapper.ModuleMapper;
import com.letfit.pojo.entity.Admin;
import com.letfit.pojo.entity.Module;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Source;
import com.letfit.service.AdminService;
import com.letfit.shiro.auth.BcryptUtil;
import com.letfit.common.CodeEnum;
import com.letfit.util.FileUtil;
import com.letfit.util.RedisUtil;
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
