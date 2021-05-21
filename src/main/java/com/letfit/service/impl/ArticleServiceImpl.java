package com.letfit.service.impl;

import com.letfit.common.CodeEnum;
import com.letfit.mongo.ArticleRepository;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Article;
import com.letfit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author cjt
 * @date 2021/5/21 11:22
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取默认文章（4个）
     * @return
     */
    @Override
    public ResultInfo<List<Article>> findDefaultArticle() {
        List<Article> articleList = new LinkedList<>();
        TypedAggregation<Article> aggregation = Aggregation.newAggregation(Article.class, Aggregation.sample(4));
        AggregationResults<Article> results = mongoTemplate.aggregate(aggregation, Article.class);
        results.forEach(result->{
            articleList.add(result);
        });
        return ResultInfo.success(CodeEnum.SUCCESS, articleList);
    }

    /**
     * 根据模块名查找文章
     * @param moduleName
     * @return
     */
    @Override
    public ResultInfo<List<Article>> findByModuleName(String moduleName) {
        List<Article> articles = articleRepository.findByModuleName(moduleName);
        return ResultInfo.success(CodeEnum.SUCCESS, articles);
    }

    /**
     * 根据文章标题模糊查询
     * @param articleTitle
     * @return
     */
    @Override
    public ResultInfo<List<Article>> findByArticleTitle(String articleTitle) {
        Pattern pattern = Pattern.compile("^.*" + articleTitle + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query();
        query.addCriteria(Criteria.where("articleTitle").regex(pattern));
        List<Article> articles = mongoTemplate.find(query, Article.class);
        return ResultInfo.success(CodeEnum.SUCCESS, articles);
    }

}
