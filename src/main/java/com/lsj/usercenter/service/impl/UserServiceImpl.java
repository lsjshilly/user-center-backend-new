package com.lsj.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.model.domain.User;
import com.lsj.usercenter.service.UserService;
import com.lsj.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author liushijie
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-07 20:59:05
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




