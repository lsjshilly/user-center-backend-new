package com.lsj.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.execption.BusinessExecption;
import com.lsj.usercenter.mapper.UserMapper;
import com.lsj.usercenter.model.domain.ResultList;
import com.lsj.usercenter.model.domain.User;
import com.lsj.usercenter.model.domain.UserCondition;
import com.lsj.usercenter.model.request.LoginRequst;
import com.lsj.usercenter.model.request.RegisterRequest;
import com.lsj.usercenter.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.lsj.usercenter.common.ErrCode.*;
import static com.lsj.usercenter.constant.UserConstant.SALT;

/**
 * @author liushijie
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-04-07 20:59:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Override
    public User register(RegisterRequest registerUser) {

        if (StringUtils.isAnyEmpty(registerUser.getUserAccount(), registerUser.getUserPassword(), registerUser.getConfirmPassword())) {
            throw new BusinessExecption(ERR_VALIDATION, "request params should not empty");
        }

        if (registerUser.getUserAccount().length() < 4) {
            throw new BusinessExecption(ERR_VALIDATION, "useaccount too short");
        }

        if (registerUser.getUserPassword().length() < 8) {
            throw new BusinessExecption(ERR_VALIDATION, "usepassword too short");
        }

        if (!registerUser.getUserPassword().equals(registerUser.getConfirmPassword())) {
            throw new BusinessExecption(ERR_VALIDATION, "inconsistent password");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", registerUser.getUserAccount());
        User existUser = this.getOne(queryWrapper);
        if (existUser != null) {
            throw new BusinessExecption(ERR_VALIDATION, "duplicate useraccount");
        }

        String encryptPassword = DigestUtils.md5Hex(SALT + registerUser.getUserPassword());

        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setUserAccount(registerUser.getUserAccount());
        user.setUserPassword(encryptPassword);
        user.setAvatar(registerUser.getAvatar());
        user.setPhone(registerUser.getPhone());
        user.setEmail(registerUser.getEmail());
        user.setGender(registerUser.getGender());
        user.setState(0);
        user.setDeleted(0);
        boolean saved = this.save(user);
        if (!saved) {
            throw new BusinessExecption(ERR_DATABASE, "save usere to db error");
        }
        return getSafeUser(user);
    }

    @Override
    public User getSafeUser(User originUser) {
        User safeUser = new User();
        safeUser.setId(originUser.getId());
        safeUser.setUsername(originUser.getUsername());
        safeUser.setUserAccount(originUser.getUserAccount());
        safeUser.setAvatar(originUser.getAvatar());
        safeUser.setGender(originUser.getGender());
        safeUser.setState(originUser.getState());
        safeUser.setCreateTime(originUser.getCreateTime());
        safeUser.setUpdateTime(originUser.getUpdateTime());
        safeUser.setAuthorizator(originUser.getAuthorizator());
        return safeUser;
    }

    @Override
    public User login(LoginRequst loginRequst) {
        if (StringUtils.isAnyEmpty(loginRequst.getUserAccount(), loginRequst.getUserPassword())) {
            throw new BusinessExecption(ERR_LOGIN_ERROR, "request params should not empty");
        }

        if (loginRequst.getUserAccount().length() < 4) {
            throw new BusinessExecption(ERR_LOGIN_ERROR, "useaccount too short");
        }

        if (loginRequst.getUserPassword().length() < 8) {
            throw new BusinessExecption(ERR_LOGIN_ERROR, "usepassword too short");
        }

        String encryptPassword = DigestUtils.md5Hex(SALT + loginRequst.getUserPassword());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", loginRequst.getUserAccount());
        queryWrapper.eq("user_password", encryptPassword);
        User existUser = this.getOne(queryWrapper);
        if (existUser == null) {
            throw new BusinessExecption(ERR_LOGIN_ERROR, "useraccount or password error");
        }
        return getSafeUser(existUser);


    }

    @Override
    public ResultList<User> searchUsers(UserCondition condition) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(condition.getUsername())) {
            queryWrapper.like("username", condition.getUsername());
        }

        if (StringUtils.isNotBlank(condition.getUserAccount())) {
            queryWrapper.eq("use_account", condition.getUsername());
        }

        if (condition.getGender() != null) {
            queryWrapper.eq("gender", condition.getGender());
        }

        if (condition.getStartTime() != null && condition.getEndTime() != null) {
            queryWrapper.between("create_time", condition.getStartTime(), condition.getEndTime());
        }

        Page<User> page = new Page<>(condition.getCurrent(), condition.getPageSize());

        Page<User> rowpage = this.page(page, queryWrapper);

        ResultList<User> resultList = new ResultList<>();
        resultList.setItems(rowpage.getRecords());
        resultList.setTotal(rowpage.getTotal());


        return resultList;
    }
}




