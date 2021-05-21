package com.letfit.service.impl;

import com.letfit.common.CodeEnum;
import com.letfit.mongo.QuestionRepository;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Question;
import com.letfit.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/20 10:41
 */
@Validated
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增一道题目
     * @param question
     */
    @Override
    public void saveOne(Question question) {
        questionRepository.save(question);
    }

    /**
     * 自由模式随机查找一道题
     * @return
     */
    @Override
    public ResultInfo<Question> getOneQuestion(){
        List<Question> questions = new LinkedList<>();
        TypedAggregation<Question> aggregation = Aggregation.newAggregation(Question.class, Aggregation.sample(1));
        AggregationResults<Question> results = mongoTemplate.aggregate(aggregation, Question.class);
        results.forEach(result->{
            questions.add(result);
        });
        if(questions.size() == 0){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL, null);
        }else{
            return ResultInfo.success(CodeEnum.SUCCESS, questions.get(0));
        }
    }

    /**
     * 自由模式根据模块名随机查找一道题
     * @param moduleName
     * @return
     */
    @Override
    public ResultInfo<Question> getOneQuestionByName(@NotBlank String moduleName){
        List<Question> questions = new LinkedList<>();
        MatchOperation match = Aggregation.match(Criteria.where("moduleName").is(moduleName));
        TypedAggregation<Question> aggregation = Aggregation.newAggregation(Question.class, match, Aggregation.sample(1));
        AggregationResults<Question> results = mongoTemplate.aggregate(aggregation, Question.class);
        results.forEach(result->{
            questions.add(result);
        });
        if(questions.size() == 0){
            return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL, null);
        }else{
            return ResultInfo.success(CodeEnum.SUCCESS, questions.get(0));
        }
    }

    /**
     * 闯关模式随机查找10道题
     * @return
     */
    @Override
    public ResultInfo<List<Question>> getQuestions() {
        List<Question> questions = new LinkedList<>();
        TypedAggregation<Question> aggregation = Aggregation.newAggregation(Question.class, Aggregation.sample(10));
        AggregationResults<Question> results = mongoTemplate.aggregate(aggregation, Question.class);
        results.forEach(result->{
            questions.add(result);
        });
        return ResultInfo.success(CodeEnum.SUCCESS, questions);
    }

    /**
     * 闯关模式根据模块名随机查找10道题
     * @param moduleName
     * @return
     */
    @Override
    public ResultInfo<List<Question>> getQuestionsByName(@NotBlank String moduleName){
        List<Question> questions = new LinkedList<>();
        MatchOperation match = Aggregation.match(Criteria.where("moduleName").is(moduleName));
        TypedAggregation<Question> aggregation = Aggregation.newAggregation(Question.class, match, Aggregation.sample(10));
        AggregationResults<Question> results = mongoTemplate.aggregate(aggregation, Question.class);
        results.forEach(result->{
            questions.add(result);
        });
        return ResultInfo.success(CodeEnum.SUCCESS, questions);
    }

}
