package com.lsj.usercenter.common;


import lombok.Getter;

@Getter
public enum ErrCode {


    ERR_SUCCESS(100001, ""),

    ERR_SYSTEM_ERROR(100002, "internal error"),

    ERR_DATABASE(100101, "database error"),

    ERR_VALIDATION(100201, "validation error"),

    ERR_PAGE_NOT_FOUND(100202, "page not found"),

    ERR_NOT_LOGIN(100203, "not login"),

    ERR_ACCESS_DENEY(100204, "access deney"),

    ERR_UPLODA_AVATAR(110101, "upload avatar error"),

    ERR_LOGIN_ERROR(110102, "login error");


    private final int code;
    private final String message;
    private String description;


    ErrCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
