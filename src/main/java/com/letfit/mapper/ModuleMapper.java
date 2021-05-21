package com.letfit.mapper;

import com.letfit.pojo.entity.Module;
import com.letfit.pojo.vo.RecordInfo;
import com.letfit.pojo.vo.UserRecordModule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cjt
 * @date 2021/4/10 15:42
 */
@Repository
public interface ModuleMapper {

    /**
     * 查询所有模块
     * @return
     */
    List<Module> getModuleList();

    /**
     * 根据用户id查询用户加入的圈子
     * @param userId
     * @return
     */
    List<UserRecordModule> getUserModule(Integer userId);

    /**
     * 首页获取用户最近打卡圈子
     * @param userId
     * @return
     */
    List<Module> getUserRecentModule(Integer userId);

    /**
     * 根据模块id查询模块
     * @param moduleId
     * @return
     */
    Module getModuleByModuleId(Integer moduleId);

    /**
     * 拿到圈子的打卡记录（每页4个）
     * @param moduleId
     * @param start
     * @param rows
     * @return
     */
    List<RecordInfo> getCirclePage(Integer moduleId, int start , int rows);

}
