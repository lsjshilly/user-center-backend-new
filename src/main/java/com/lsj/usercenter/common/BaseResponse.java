package com.lsj.usercenter.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.lsj.usercenter.common.ErrCode.ERR_SUCCESS;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private int code;

    private String message;

    private String description;

    private T data;


    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ERR_SUCCESS.getCode(), "", "", data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(ERR_SUCCESS.getCode(), "", "", null);
    }


    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description, null);
    }


    public static <T> BaseResponse<T> error(ErrCode errCode, String description) {
        return new BaseResponse<>(errCode.getCode(), errCode.getMessage(), description, null);
    }

}
