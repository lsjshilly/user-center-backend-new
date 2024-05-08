package com.lsj.usercenter.model.dto;

import com.lsj.usercenter.model.common.ErrCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.lsj.usercenter.model.common.ErrCode.ERR_SUCCESS;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;


    public static Result success(Object data) {
        return new Result(ERR_SUCCESS.getCode(), "", data);
    }

    public static Result success() {
        return new Result(ERR_SUCCESS.getCode(), "", null);
    }


    public static Result error(int code, String message) {
        return new Result(code, message, null);
    }


    public static Result error(ErrCode errCode, String message) {
        return new Result(errCode.getCode(), message, null);
    }

}
