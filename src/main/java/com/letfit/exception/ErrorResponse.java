package com.letfit.exception;

import lombok.Data;

/**
 * @author cjt
 * @date 2021/4/27 17:11
 */
@Data
public class ErrorResponse {

    private String message;
    private String errorTypeName;

    public ErrorResponse(Exception e) {
        this(e.getClass().getName(), e.getMessage());
    }

    public ErrorResponse(String errorTypeName, String message) {
        this.errorTypeName = errorTypeName;
        this.message = message;
    }

}
