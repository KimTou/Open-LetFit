package com.letfit.service;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Source;
import com.letfit.pojo.vo.IndexInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/7 22:22
 */
@Service
public interface SourceService {

    /**
     * 获取首页信息
     * @param userId
     * @return
     */
    ResultInfo<IndexInfo> getIndexInfo(Integer userId);

    /**
     * 获取模块对应的资源
     * @param moduleId
     * @return
     */
    ResultInfo<List<Source>> getSourceByModuleId(Integer moduleId);

    /**
     * 根据标题关键字模糊查询资源
     * @param title
     * @return
     */
    ResultInfo<List<Source>> searchSource(String title);

}
