package com.openletfit.mapper;

import com.openletfit.pojo.entity.Source;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/7 21:39
 */
@Repository
public interface SourceMapper {

    /**
     * 获取首页推荐视频
     * @param moduleIdList
     * @return
     */
    List<Source> getIndexSource(List<Integer> moduleIdList);

    /**
     * 获取模块对应的资源
     * @param moduleId
     * @return
     */
    List<Source> getSourceByModuleId(Integer moduleId);

    /**
     * 获取默认推荐
     * @return
     */
    List<Source> getDefaultSource();

    /**
     * 根据标题关键字模糊查询资源
     * @param title
     * @return
     */
    List<Source> searchSource(String title);

}
