package com.lsj.usercenter.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private int code;

    private String message;

    private String description;

    private T data;


    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "", "", data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(0, "", "", null);
    }


    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description, null);
    }

}
