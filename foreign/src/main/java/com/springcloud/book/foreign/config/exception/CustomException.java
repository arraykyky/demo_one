package com.springcloud.book.foreign.config.exception;

import com.alibaba.fastjson.JSON;

public class CustomException extends Exception {

    private String code;
    private String message;

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**************************************请求时参数验证异常信息**********************************************/
    public static final String INCOMPLETE_PARAMETERS_CODE = "500_0000";
    public static final String INCOMPLETE_PARAMETERS_MSG = "参数不完整";

    public static void main(String[] args) throws CustomException {
       CustomException customException = new CustomException(CustomException.INCOMPLETE_PARAMETERS_MSG);
       throw customException;
    }
}
