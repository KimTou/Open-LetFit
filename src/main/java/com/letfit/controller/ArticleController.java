package com.letfit.controller;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Article;
import com.letfit.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/21 15:51
 */
@Api(tags = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(("获取默认文章（4个）"))
    @GetMapping("/findDefaultArticle")
    public ResultInfo<List<Article>> findDefaultArticle(){
        return articleService.findDefaultArticle();
    }

    @ApiOperation("根据模块名查找文章")
    @GetMapping("/findByModuleName/{moduleName}")
    public ResultInfo<List<Article>> findByModuleName(@PathVariable("moduleName") String moduleName){
        return articleService.findByModuleName(moduleName);
    }

    @ApiOperation("根据文章标题模糊查询")
    @GetMapping("/findByArticleTitle/{articleTitle}")
    public ResultInfo<List<Article>> findByArticleTitle(@PathVariable("articleTitle") String articleTitle){
        return articleService.findByArticleTitle(articleTitle);
    }

}
