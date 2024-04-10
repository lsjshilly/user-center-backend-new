package com.lsj.usercenter.execption;


import lombok.Getter;

@Getter
public class BusinessExecption extends RuntimeException {
    private final int code;

    private final String description;


    public BusinessExecption(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
}
