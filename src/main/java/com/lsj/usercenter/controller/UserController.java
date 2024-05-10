package com.lsj.usercenter.controller;

import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.dto.LoginFromDTO;
import com.lsj.usercenter.model.dto.RegisterFromDTO;
import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.model.dto.UserDTO;
import com.lsj.usercenter.model.entity.UserInfo;
import com.lsj.usercenter.model.query.UserQuery;
import com.lsj.usercenter.service.UserInfoService;
import com.lsj.usercenter.service.UserService;
import com.lsj.usercenter.utils.UserHodler;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-center/user")
public class UserController {


    @Resource
    private UserService userService;


    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginFromDTO loginFromDTO) {
        return userService.doLogin(loginFromDTO);
    }


    @PostMapping("/register")
    public Result register(@RequestBody RegisterFromDTO registerFromDTO) {
        return userService.register(registerFromDTO);
    }

    @GetMapping("/permissions")
    public Result permissions() {
        UserDTO userDTO = UserHodler.get();
        if (userDTO == null) {
            return Result.error(ErrCode.ERR_NOT_LOGIN, "未登录");
        }
        return Result.success(userDTO);
    }


    @GetMapping("/me")
    public Result info() {
        UserDTO userDTO = UserHodler.get();
        if (userDTO == null) {
            return Result.error(ErrCode.ERR_NOT_LOGIN, "未登录");
        }
        UserInfo userInfo = userInfoService.query().eq("user_id", userDTO.getId()).one();
        return Result.success(userInfo);
    }

    @GetMapping("/search")
    public Result getUserInfos(UserQuery userQuery) {
        return userInfoService.getUserInfos(userQuery);
    }

}
