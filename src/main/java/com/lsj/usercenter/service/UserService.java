package com.lsj.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsj.usercenter.model.dto.LoginFromDTO;
import com.lsj.usercenter.model.dto.RegisterFromDTO;
import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.model.entity.User;

/**
 * @author liushijie
 * @description 针对表【tb_user】的数据库操作Service
 * @createDate 2024-05-07 23:21:15
 */
public interface UserService extends IService<User> {

    Result doLogin(LoginFromDTO loginFromDTO);

    Result register(RegisterFromDTO registerFromDTO);
}
