package com.lsj.usercenter.model.common;


import lombok.Getter;

@Getter
public enum ErrCode {

    /**
     * 成功
     */
    ERR_SUCCESS(100001, ""),
    /**
     * 系统内部错误
     */
    ERR_SYSTEM_ERROR(100002, "internal error"),
    /**
     * 数据库错误
     */
    ERR_DATABASE(100101, "database error"),
    /**
     * 校验错误
     */
    ERR_VALIDATION(100201, "validation error"),

    /**
     * 不存在
     */
    ERR_PAGE_NOT_FOUND(100202, "page not found"),
    /**
     * 未登录
     */
    ERR_NOT_LOGIN(100203, "not login"),

    /**
     * 无权限
     */
    ERR_ACCESS_DENEY(100204, "access deney"),

    /**
     * 头像上传失败
     */
    ERR_UPLODA_AVATAR(110101, "upload avatar error"),

    /**
     * 登录失败
     */
    ERR_LOGIN_ERROR(110102, "login error");


    private final int code;
    private final String message;
    private String description;


    ErrCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
