package com.letfit.exception;

import com.letfit.pojo.dto.ResultInfo;
import com.letfit.common.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * @author cjt
 * @date 2021/4/16 0:09
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数为空异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, ParamNullException.class})
    public ResultInfo<ErrorResponse> paramNull(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(ex);
        log.error("paramNull Exception 空参异常: {}", errorResponse);
        return ResultInfo.error(CodeEnum.NULL_PARAM, errorResponse);
    }

    /**
     * 使用Valid注解判断参数为空异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex);
        log.error("paramNull Exception 空参异常: {}", errorResponse);
        return ResultInfo.error(CodeEnum.NULL_PARAM, errorResponse);
    }

    /**
     * 参数格式异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultInfo<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex);
        log.error("ConstraintViolationException 参数格式异常: {}", errorResponse);
        return ResultInfo.error(CodeEnum.PARAM_PATTERN_INVALID, errorResponse);
    }

    /**
     * 运行时异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultInfo<String> runTimeException(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex);
        log.error("运行时异常: {}", errorResponse);
        return ResultInfo.error(CodeEnum.BAD_REQUEST);
    }

    /**
     * 文件上传异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public ResultInfo<String> multipartException(MultipartException ex){
        Throwable cause = ex.getCause();
        if(cause instanceof FileSizeLimitExceededException){
            log.error("单个上传文件大小超过限制: {}", ex.getMessage());
        }
        else if(cause instanceof SizeLimitExceededException){
            log.error("总上传文件大小超过限制: {}", ex.getMessage());
        }
        else{
            log.error("上传文件大小超过限制: {}", ex.getMessage());
        }
        return ResultInfo.error(CodeEnum.BAD_REQUEST, ex.getMessage());
    }

    /**
     * 数组越界异常
     * @param ex
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultInfo<String>  nullPointerException(NullPointerException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex);
        log.error("数组越界: {}", errorResponse);
        return ResultInfo.error(CodeEnum.PARAM_NOT_IDEAL, ex.getMessage());
    }

    /**
     * io异常
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    public ResultInfo<String> ioException(IOException ex){
        log.error("io Exception: {}", ex.getMessage());
        return ResultInfo.error(CodeEnum.FILE_UPLOAD_FAIL, ex.getMessage());
    }

    /**
     * 处理所有异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultInfo<String> exception(Exception ex){
        log.error("Exception: {}", ex.getMessage());
        return ResultInfo.error(CodeEnum.BAD_REQUEST, ex.getMessage());
    }

}
