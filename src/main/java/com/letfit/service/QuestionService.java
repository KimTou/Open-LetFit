package com.letfit.service;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Question;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author cjt
 * @date 2021/5/20 10:40
 */
@Validated
@Service
public interface QuestionService {

    /**
     * 新增一道题目
     * @param question
     */
    void saveOne(Question question);

    /**
     * 自由模式根据模块名随机查找一道题
     * @return
     */
    ResultInfo<Question> getOneQuestion();

    /**
     * 自由模式根据模块名随机查找一道题
     * @param moduleName
     * @return
     */
    ResultInfo<Question> getOneQuestionByName(@NotBlank String moduleName);

    /**
     * 闯关模式随机查找10道题
     * @return
     */
    ResultInfo<List<Question>> getQuestions();

    /**
     * 闯关模式根据模块名随机查找10道题
     * @param moduleName
     * @return
     */
    ResultInfo<List<Question>> getQuestionsByName(@NotBlank String moduleName);

}
