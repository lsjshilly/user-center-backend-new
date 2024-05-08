package com.lsj.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.mapper.UserInfoMapper;
import com.lsj.usercenter.model.dto.ListResult;
import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.model.entity.UserInfo;
import com.lsj.usercenter.model.query.UserQuery;
import com.lsj.usercenter.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.lsj.usercenter.model.constant.SystemConstants.DEFAULT_PAGESIZE;
import static com.lsj.usercenter.model.constant.SystemConstants.MAX_PAGESIZE;

/**
 * @author liushijie
 * @description 针对表【tb_user_info】的数据库操作Service实现
 * @createDate 2024-05-07 23:21:15
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Override
    public Result getUserInfos(UserQuery userQuery) {

        if (userQuery.getPageSize() > MAX_PAGESIZE) {
            userQuery.setPageSize(MAX_PAGESIZE);
        }

        if (userQuery.getPageSize() <= 0) {
            userQuery.setPageSize(DEFAULT_PAGESIZE);
        }


        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();


        if (userQuery.getUserId() != null) {
            queryWrapper.eq("user_id", userQuery.getUserId());
        } else {

            if (StringUtils.isNotBlank(userQuery.getUsername())) {
                queryWrapper.eq("username", userQuery.getUsername());
            }

            if (StringUtils.isNotBlank(userQuery.getNickname())) {
                queryWrapper.eq("nickname", userQuery.getNickname());
            }

            if (userQuery.getGender() != null) {
                queryWrapper.eq("gender", userQuery.getGender());
            }
            if (userQuery.getTags() != null && !userQuery.getTags().isEmpty()) {
                for (String tag : userQuery.getTags()) {
                    queryWrapper.like("tags", tag);
                }
            }
        }
        Page<UserInfo> page = new Page<>(userQuery.getPageNum(), userQuery.getPageSize());
        Page<UserInfo> userInfoPage = this.page(page, queryWrapper);

        ListResult result = new ListResult();
        result.setItems(userInfoPage.getRecords());
        result.setTotal(userInfoPage.getTotal());
        return Result.success(result);
    }
}




