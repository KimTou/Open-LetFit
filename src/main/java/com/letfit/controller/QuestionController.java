package com.letfit.controller;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Question;
import com.letfit.service.QuestionService;
import com.letfit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cjt
 * @date 2021/5/20 16:11
 */
@Api(tags = "答题系统")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @ApiOperation("自由模式随机获取一道题")
    @GetMapping("/freedom/getOneQuestion")
    public ResultInfo<Question> getOneQuestion(){
        return questionService.getOneQuestion();
    }

    @ApiOperation("自由模式根据模块名随机查找一道题")
    @GetMapping("/freedom/getOneQuestionByName/{moduleName}")
    public ResultInfo<Question> getOneQuestionByName(@PathVariable("moduleName") String moduleName){
        return questionService.getOneQuestionByName(moduleName);
    }

    @ApiOperation("闯关模式随机获取10道题")
    @GetMapping("/through/getQuestions")
    public ResultInfo<List<Question>> getQuestions(){
        return questionService.getQuestions();
    }

    @ApiOperation("闯关模式根据模块名随机查找10道题")
    @GetMapping("/through/getQuestionsByName/{moduleName}")
    public ResultInfo<List<Question>> getQuestionsByName(@PathVariable("moduleName") String moduleName){
        return questionService.getQuestionsByName(moduleName);
    }

    @ApiOperation("用户累加积分")
    @PostMapping("/updateUserPoint")
    public ResultInfo<?> updateUserPoint(@RequestParam("userId") Integer userId, @RequestParam("point") Integer point){
        return userService.updateUserPoint(userId, point);
    }


}
