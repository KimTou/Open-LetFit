package com.letfit.service;

import com.letfit.pojo.entity.Admin;
import com.letfit.pojo.entity.Module;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Source;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
/**
 * @author cjt
 * @date 2021/4/10 11:00
 */
@Service
public interface AdminService {

    /**
     * 添加模块
     * @param name 模块名称
     * @param file 模块图片
     * @param suffixName 图片后缀名
     * @return
     */
    ResultInfo<Module> addModule(String name, BufferedInputStream file, String suffixName);

    /**
     * 上传资源
     * @param moduleName
     * @param sourceTitle
     * @param file
     * @param suffixName
     * @return
     */
    ResultInfo<Source> addSource(String moduleName, String sourceTitle, BufferedInputStream file, String suffixName);

    /**
     * 上传视频封面
     * @param sourceId
     * @param file
     * @param suffixName
     * @return
     */
    ResultInfo<Source> uploadSourceCover(Integer sourceId, BufferedInputStream file, String suffixName);

    /**
     * 添加管理员
     * @param admin
     */
    void insertAdmin(Admin admin);

    /**
     * 根据name查找管理员
     * @param adminName
     * @return
     */
    Admin findAdminByName(String adminName);

}
