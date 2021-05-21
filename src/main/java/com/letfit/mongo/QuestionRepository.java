package com.letfit.mongo;

import com.letfit.pojo.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/20 10:21
 */
public interface QuestionRepository extends MongoRepository<Question, String> {

    /**
     * 根据模块查找题目
     * @param moduleName
     * @return
     */
    List<Question> findByModuleName(String moduleName);

}
