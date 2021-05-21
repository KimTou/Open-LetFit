package com.letfit.mongo;

import com.letfit.pojo.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/21 10:56
 */
public interface ArticleRepository extends MongoRepository<Article, String> {

    /**
     * 根据模块名查找文章
     * @param moduleName
     * @return
     */
    List<Article> findByModuleName(String moduleName);

}
