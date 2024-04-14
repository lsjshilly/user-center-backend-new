package com.lsj.usercenter.controller;


import com.lsj.usercenter.aspect.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static com.lsj.usercenter.constant.UserConstant.USER_CENTER_REQUEST;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Component
public abstract class BaseController {

    public RequestContext getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (Objects.requireNonNull(requestAttributes)).getRequest();
        return (RequestContext) requestAttributes.getAttribute(USER_CENTER_REQUEST, SCOPE_REQUEST);
    }
}
