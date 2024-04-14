package com.lsj.usercenter.aspect;


import com.lsj.usercenter.common.BaseResponse;
import com.lsj.usercenter.execption.BusinessExecption;
import com.lsj.usercenter.mapper.UserMapper;
import com.lsj.usercenter.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

import static com.lsj.usercenter.common.ErrCode.*;
import static com.lsj.usercenter.constant.UserConstant.USER_CENTER_REQUEST;
import static com.lsj.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Aspect
@Component
@Slf4j
public class AspectExexutor {


    private UserMapper userMapper;

    private static String getRequiredAuth(MethodSignature signature) {
        Method method = signature.getMethod();
        Auth annotation = method.getAnnotation(Auth.class);
        if (annotation == null) {
            return "user::nologin";
        }
        return annotation.value();
    }

    @Pointcut("@annotation(com.lsj.usercenter.aspect.Auth)")
    private void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (Objects.requireNonNull(requestAttributes)).getRequest();
        RequestContext requestContext = buildRequst(request);
        requestAttributes.setAttribute(USER_CENTER_REQUEST, requestContext, SCOPE_REQUEST);


        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = buildOperationLog(requestContext, signature);
        log.info("##build request: {} {} {} {}", operationLog.getMethod(),
                operationLog.getRequstUri(), operationLog.getOperationUser(), operationLog.getMethodSignature());

        String requiredAuth = getRequiredAuth(signature);

        User user = requestContext.getUser();

        if (user == null && !requiredAuth.equals("user::nologin")) {
            return BaseResponse.error(ERR_NOT_LOGIN, "user not login");
        }

        if (user != null && !user.getAuthorizator().equals(requiredAuth) && !user.getAuthorizator().equals("user::admin")) {
            return BaseResponse.error(ERR_ACCESS_DENEY, "insufficient permissions");
        }

        try {
            Object result = joinPoint.proceed();
            if (result instanceof BaseResponse<?>) {
                if (((BaseResponse<?>) result).getCode() == ERR_SUCCESS.getCode()) {
                    operationLog.setResult("success");
                } else {
                    operationLog.setResult("error");
                    operationLog.setErrMsg(((BaseResponse<?>) result).getDescription());
                }
            }
            return result;
        } catch (BusinessExecption e) {
            operationLog.setResult("error");
            operationLog.setErrMsg(e.getDescription());
            throw e;
        } catch (Throwable e) {
            operationLog.setResult("error");
            operationLog.setErrMsg(e.getMessage());
            throw new BusinessExecption(ERR_SYSTEM_ERROR, e.getMessage());
        } finally {
            operationLog.submit();
        }


    }


    public RequestContext buildRequst(HttpServletRequest request) {
        User attribute = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            return new RequestContext(request, null);
        }

        User user = userMapper.selectById(attribute.getId());

        return new RequestContext(request, user);
    }


    public OperationLog buildOperationLog(RequestContext requestContext, MethodSignature signature) {
        OperationLog operationLog = new OperationLog();
        operationLog.setMethod(requestContext.getRequest().getMethod());
        operationLog.setRequstUri(requestContext.getRequest().getRequestURI());
        operationLog.setMethodSignature(signature.getMethod().getName());
        operationLog.setOperationUser(requestContext.getUser() == null ? "anonymous" : requestContext.getUser().getUsername());
        return operationLog;
    }


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
