package com.lsj.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsj.usercenter.model.domain.ResultList;
import com.lsj.usercenter.model.domain.User;
import com.lsj.usercenter.model.domain.UserCondition;
import com.lsj.usercenter.model.request.LoginRequst;
import com.lsj.usercenter.model.request.RegisterRequest;

/**
 * @author liushijie
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-04-07 20:59:05
 */
public interface UserService extends IService<User> {

    User register(RegisterRequest registerRequest);

    User getSafeUser(User originUser);

    User login(LoginRequst loginRequst);

    ResultList<User> searchUsers(UserCondition condition);
}
