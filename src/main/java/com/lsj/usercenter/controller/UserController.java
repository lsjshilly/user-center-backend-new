package com.lsj.usercenter.controller;

import com.lsj.usercenter.aspect.Auth;
import com.lsj.usercenter.aspect.RequestContext;
import com.lsj.usercenter.common.BaseResponse;
import com.lsj.usercenter.model.domain.ResultList;
import com.lsj.usercenter.model.domain.User;
import com.lsj.usercenter.model.domain.UserCondition;
import com.lsj.usercenter.model.request.LoginRequst;
import com.lsj.usercenter.model.request.RegisterRequest;
import com.lsj.usercenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lsj.usercenter.common.ErrCode.*;
import static com.lsj.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {


    private UserService userService;

    @PostMapping("/register")
    @Auth("user::nologin")
    public BaseResponse<User> register(@RequestBody RegisterRequest registerRequest) {
        if (StringUtils.isAnyEmpty(registerRequest.getUserAccount(), registerRequest.getUserPassword(), registerRequest.getConfirmPassword())) {
            return BaseResponse.error(ERR_VALIDATION.getCode(), ERR_VALIDATION.getMessage(), "request params should not empty");
        }

        User registeredUser = userService.register(registerRequest);

        return BaseResponse.success(registeredUser);
    }


    @PostMapping("/login")
    @Auth("user::nologin")
    public BaseResponse<User> login(@RequestBody LoginRequst loginRequst) {
        if (StringUtils.isAnyEmpty(loginRequst.getUserAccount(), loginRequst.getUserPassword())) {
            return BaseResponse.error(ERR_VALIDATION.getCode(), ERR_VALIDATION.getMessage(), "request params should not empty");
        }
        User user = userService.login(loginRequst);
        RequestContext requestContext = getRequest();
        requestContext.getRequest().getSession().setAttribute(USER_LOGIN_STATE, user);
        return BaseResponse.success(user);
    }


    @GetMapping("/currentUser")
    @Auth
    public BaseResponse<User> currentUser() {
        RequestContext requestContext = getRequest();
        User attribute = (User) requestContext.getRequest().getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            return BaseResponse.error(ERR_NOT_LOGIN, "user login state not exist");
        }

        User user = userService.getById(attribute.getId());
        User safeUser = userService.getSafeUser(user);

        return BaseResponse.success(safeUser);
    }


    @PostMapping("/logout")
    @Auth
    public BaseResponse<User> logout(HttpServletRequest request) {
        request.getSession().setAttribute(USER_LOGIN_STATE, null);
        return BaseResponse.success();
    }


    @GetMapping("/search")
    @Auth("user::admin")
    public BaseResponse<ResultList<User>> searchUsers(UserCondition condition) {
        ResultList<User> users = userService.searchUsers(condition);
        return BaseResponse.success(users);
    }

    @DeleteMapping("/{id}")
    @Auth("user::admin")
    public BaseResponse<Integer> delete(@PathVariable("id") Integer id) {
        boolean removed = userService.removeById(id);
        if (removed) {
            return BaseResponse.success(0);
        }
        return BaseResponse.success(-1);
    }

    @PutMapping("/modify")
    @Auth()
    public BaseResponse<Integer> update(@RequestBody User user) {
        boolean updated = userService.updateById(user);

        if (updated) {
            return BaseResponse.success(0);

        }
        return BaseResponse.error(ERR_SYSTEM_ERROR, "update user error");
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
