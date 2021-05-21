package com.letfit.service;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/21 11:22
 */
@Service
public interface ArticleService {

    /**
     * 默认文章
     * @return
     */
    ResultInfo<List<Article>> findDefaultArticle();

    /**
     * 根据模块名查找文章
     * @param moduleName
     * @return
     */
    ResultInfo<List<Article>> findByModuleName(String moduleName);

    /**
     * 根据文章标题模糊查询
     * @param articleTitle
     * @return
     */
    ResultInfo<List<Article>> findByArticleTitle(String articleTitle);

}
