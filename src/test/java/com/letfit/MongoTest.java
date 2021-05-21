package com.letfit;

import com.letfit.mongo.ArticleRepository;
import com.letfit.mongo.QuestionRepository;
import com.letfit.pojo.entity.Article;
import com.letfit.pojo.entity.Item;
import com.letfit.pojo.entity.Question;
import com.letfit.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author cjt
 * @date 2021/5/20 15:05
 */
@SpringBootTest
public class MongoTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {

    }

    @Test
    void save(){
        Question question = new Question();
        //题目标题
        question.setQuestionTitle("太极拳运动能加强中枢神经系统功能");

        List<Item> items = new LinkedList<>();

        Item item1 = new Item();
        item1.setWord('A');
        item1.setContent("对");
        items.add(item1);

        Item item2 = new Item();
        item2.setWord('B');
        item2.setContent("错");
        items.add(item2);

        /*Item item3 = new Item();
        item3.setWord('C');
        item3.setContent("改造");
        items.add(item3);*/

        /*Item item4 = new Item();
        item4.setWord('D');
        item4.setContent("水（能量饮料也行）和一些能量食品");
        items.add(item4);*/

        question.setItems(items);

        //题目解析
        question.setAnalyze(null);
        //题目答案
        question.setAnswer('A');
        //题目模块名
        question.setModuleName("太极");
        question.setImagePath(null);
        questionService.saveOne(question);
    }

    @Test
    void findByModuleName(){
        List<Article> articles = articleRepository.findByModuleName("篮球");
        System.out.println(articles);
    }

    @Test
    void regexFind(){
        String questionTitle = "起源";
        Pattern pattern = Pattern.compile("^.*" + questionTitle + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query();
        query.addCriteria(Criteria.where("questionTitle").regex(pattern));
        List<Question> articles = mongoTemplate.find(query, Question.class);
        System.out.println(articles);
    }

    @Test
    void count(){
        Query query = new Query(Criteria.where("moduleName").is("跑步"));
        long count = mongoTemplate.count(query, Question.class);
        System.out.println(count);
    }

    @Test
    void find(){
        //Query query=new Query(Criteria.where("moduleName").is("篮球"));
        MatchOperation match = Aggregation.match(Criteria.where("moduleName").is("篮球"));
        TypedAggregation<Question> aggregation = Aggregation.newAggregation(Question.class, match, Aggregation.sample(10));
        AggregationResults<Question> results = mongoTemplate.aggregate(aggregation, Question.class);
        System.out.println(results);
        results.forEach(result->{
            System.out.println(result);
        });
    }


}
