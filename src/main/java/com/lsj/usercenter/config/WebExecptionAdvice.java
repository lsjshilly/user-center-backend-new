package com.lsj.usercenter.config;


import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.model.execption.BusinessExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lsj.usercenter.model.common.ErrCode.ERR_SYSTEM_ERROR;

@RestControllerAdvice
@Slf4j
public class WebExecptionAdvice {


    @ExceptionHandler(BusinessExecption.class)
    public Result handler(BusinessExecption e) {
        log.error("#error occured: code {} message {} ", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handlerbadRequest(RuntimeException e) {
        log.error("#error occured: message {}", e.getMessage());
        return Result.error(ERR_SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
