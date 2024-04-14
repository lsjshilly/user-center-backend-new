package com.lsj.usercenter.aspect;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Data
@Slf4j
public class OperationLog {

    private String method;

    private String requstUri;


    private String methodSignature;


    private String result;

    private String operationUser;

    private String operationName;

    private String errMsg;


    public void submit() {
        if (StringUtils.equals(this.result, "success")) {
            log.info("##Successed: {} {} {} {} {}", this.method,
                    this.requstUri, this.methodSignature, this.operationUser, this.result);
        } else if (StringUtils.equals(this.result, "error")) {
            log.info("##Errord: {} {} {} {} {} {}", this.method,
                    this.requstUri, this.methodSignature, this.operationUser, this.result, this.errMsg);
        } else {
            log.info("##Unknow: {} {} {} {} {}", this.method,
                    this.requstUri, this.methodSignature, this.operationUser, this.result);
        }
        
    }

}
