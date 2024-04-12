package com.lsj.usercenter.execption;


import com.lsj.usercenter.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lsj.usercenter.common.ErrCode.ERR_SYSTEM_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExecptionHandler {


    @ExceptionHandler(BusinessExecption.class)
    public BaseResponse<Void> handler(BusinessExecption e) {
        log.error("#error occured: code {} message {} descripton {}", e.getCode(), e.getMessage(), e.getDescription());
        return BaseResponse.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<Void> handlerbadRequest(RuntimeException e) {
        log.error("#error occured: message {}", e.getMessage());
        return BaseResponse.error(ERR_SYSTEM_ERROR.getCode(), ERR_SYSTEM_ERROR.getMessage(), e.getMessage());
    }
}
