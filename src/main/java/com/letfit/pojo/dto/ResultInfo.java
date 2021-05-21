package com.letfit.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.letfit.common.CodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cjt
 * @date 2021/4/8 16:52
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("数据传输对象")
public class ResultInfo<T> {

    @ApiModelProperty(required = true, notes = "响应状态码", example = "200")
    private int code;

    @ApiModelProperty(required = true, notes = "响应状态信息", example = "OK（请求成功）")
    private String msg;

    @ApiModelProperty("响应返回数据")
    private T data;

    /**
     * 成功响应（有数据）
     * @param codeEnum
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultInfo<T> success (CodeEnum codeEnum, T data){
        return new ResultInfo<T>(codeEnum, data);
    }

    public static <T> ResultInfo<T> success (Integer code, String msg, T data){
        return new ResultInfo<T>(code, msg, data);
    }

    /**
     * 成功响应（无数据）
     * @param codeEnum
     * @param <T>
     * @return
     */
    public static <T> ResultInfo<T> success (CodeEnum codeEnum){
        return new ResultInfo<T>(codeEnum);
    }

    /**
     * 错误响应
     * @param codeEnum
     * @param <T>
     * @return
     */
    public static <T> ResultInfo<T> error (CodeEnum codeEnum){
        return new ResultInfo<>(codeEnum);
    }

    public static <T> ResultInfo<T> error (CodeEnum codeEnum, T data){
        return new ResultInfo<>(codeEnum, data);
    }

    public static <T> ResultInfo<T> error (Integer code, String msg){
        return new ResultInfo<>(code,msg);
    }

    public ResultInfo (CodeEnum codeEnum){
        this.code = codeEnum.getCode();
        this.msg =codeEnum.getMsg();
    }

    public ResultInfo (CodeEnum codeEnum, T data){
        this(codeEnum);
        this.data = data;
    }

    public ResultInfo (Integer code, String msg){
        this.code = code;
        this.msg =msg;
    }

    public ResultInfo (Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultInfo (){ }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
