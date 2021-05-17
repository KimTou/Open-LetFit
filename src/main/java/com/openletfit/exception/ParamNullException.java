package com.openletfit.exception;

/**
 * @author cjt
 * @date 2021/4/16 0:01
 */
public class ParamNullException extends RuntimeException{

    private final String paramName;
    private final String paramType;

    public ParamNullException(String paramName, String paramType) {
        super("");
        this.paramName = paramName;
        this.paramType = paramType;
    }

    @Override
    public String getMessage() {
        return "Required " + this.paramType + " parameter '" + this.paramName + "' must be not null";
    }

    public final String getParamName() {
        return this.paramName;
    }

    public final String getParamType() {
        return this.paramType;
    }


}
