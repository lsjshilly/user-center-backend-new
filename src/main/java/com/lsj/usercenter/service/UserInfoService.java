package com.lsj.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.model.entity.UserInfo;
import com.lsj.usercenter.model.query.UserQuery;

/**
 * @author liushijie
 * @description 针对表【tb_user_info】的数据库操作Service
 * @createDate 2024-05-07 23:21:15
 */
public interface UserInfoService extends IService<UserInfo> {

    Result getUserInfos(UserQuery userQuery);
}
