package com.letfit.controller;

import com.letfit.aop.annotation.CheckRepeatSubmit;
import com.letfit.aop.annotation.NullCheck;
import com.letfit.pojo.entity.Comment;
import com.letfit.pojo.entity.Record;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.vo.RecordInfo;
import com.letfit.service.RecordService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author cjt
 * @date 2021/4/18 23:14
 */
@Api(tags = "打卡记录(说说) 有关请求接口")
@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService){
        this.recordService = recordService;
    }

    @ApiOperation("发布打卡（有图片）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "moduleId", value = "模块id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "recordContent", value = "打卡记录内容", required = true,dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "file", value = "打卡图片", required = true, dataType = "MultipartFile", dataTypeClass = MultipartFile.class)
    })
    @CheckRepeatSubmit
    @PostMapping("/putRecord")
    public ResultInfo<Record> putRecord(@RequestParam("moduleId") Integer moduleId,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("recordContent") String recordContent,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        //文件名
        String fileName = file.getOriginalFilename();
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        BufferedInputStream bufferedInputStream =new BufferedInputStream(fileInputStream);
        //文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        return recordService.putRecord(moduleId,userId,recordContent,bufferedInputStream,suffixName);
    }

    @ApiOperation("发布打卡（没有图片）")
    @CheckRepeatSubmit
    @PostMapping("/putRecordWithoutImg")
    public ResultInfo<Record> putRecordWithoutImg(@RequestParam("moduleId") Integer moduleId,
                                                  @RequestParam("userId") Integer userId,
                                                  @RequestParam("recordContent") String recordContent) {
        return recordService.putRecordWithoutImg(moduleId,userId,recordContent);
    }

    @ApiOperation("点赞打卡")
    @ApiResponses({
        @ApiResponse(code = 200, message = "点赞成功"),
        @ApiResponse(code = 10003, message = "参数为空")
    })
    @CheckRepeatSubmit
    @GetMapping("/likeRecord")
    public ResultInfo<Integer> likeRecord(@NullCheck Integer userId, @NullCheck Integer recordId){
        return recordService.likeRecord(String.valueOf(userId),String.valueOf(recordId));
    }

    @ApiOperation("通过用户id查找打卡记录")
    @GetMapping("/getRecordListByUserId/{userId}/{moduleId}/{currentPage}")
    public ResultInfo<List<Record>> getRecordListByUserId(@PathVariable("userId") Integer userId,
                                                          @PathVariable("moduleId") Integer moduleId,
                                                          @PathVariable("currentPage") Integer currentPage){
        return recordService.getRecordListByUserId(userId, moduleId, currentPage);
    }

    @ApiOperation(("发表评论"))
    @PostMapping("/insertComment")
    public ResultInfo<?> insertComment(Comment comment){
        return recordService.insertComment(comment);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "获取完成"),
            @ApiResponse(code = 10003, message = "未查找到该打卡记录")
    })
    @ApiOperation(("获取打卡记录详细信息"))
    @GetMapping("/getRecordInfo/{recordId}/{userId}")
    public ResultInfo<RecordInfo> getRecordInfo(@PathVariable("recordId") Integer recordId,
                                                @PathVariable("userId") Integer userId){
        return recordService.getRecordInfo(recordId, userId.toString());
    }

}
