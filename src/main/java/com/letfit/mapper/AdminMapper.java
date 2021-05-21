package com.letfit.mapper;

import com.letfit.pojo.entity.Admin;
import com.letfit.pojo.entity.Module;
import com.letfit.pojo.entity.Source;
import org.springframework.stereotype.Repository;

/**
 * @author cjt
 * @date 2021/4/9 17:04
 */
@Repository
public interface AdminMapper {

    /**
     * 新增模块并返回主键
     * @param module
     * @return
     */
    int insertModule(Module module);

    /**
     * 根据id查找module
     * @param moduleId
     * @return
     */
    Module findModuleById(Integer moduleId);

    /**
     * 根据name查找module
     * @param moduleName
     * @return
     */
    Module findModuleByName(String moduleName);

    /**
     * 上传资源
     * @param source
     * @return
     */
    int uploadSource(Source source);

    /**
     * 上传资源封面
     * @param sourceId
     * @param sourceCover
     */
    void uploadSourceCover(Integer sourceId, String sourceCover);

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
