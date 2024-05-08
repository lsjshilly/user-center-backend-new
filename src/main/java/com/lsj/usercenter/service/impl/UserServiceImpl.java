package com.lsj.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.mapper.UserMapper;
import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.dto.LoginFromDTO;
import com.lsj.usercenter.model.dto.RegisterFromDTO;
import com.lsj.usercenter.model.dto.Result;
import com.lsj.usercenter.model.dto.UserDTO;
import com.lsj.usercenter.model.entity.User;
import com.lsj.usercenter.model.entity.UserInfo;
import com.lsj.usercenter.service.UserInfoService;
import com.lsj.usercenter.service.UserService;
import com.lsj.usercenter.utils.RegexUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.lsj.usercenter.model.constant.RedisConstants.*;
import static com.lsj.usercenter.model.constant.UserConstant.*;

/**
 * @author liushijie
 * @description 针对表【tb_user】的数据库操作Service实现
 * @createDate 2024-05-07 23:21:15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserInfoService userInfoService;

    @Override
    public Result doLogin(LoginFromDTO loginFromDTO) {

        if (ACCOUNT_LOGIN_TYPE.equals(loginFromDTO.getLoginType())) {
            return accountLogin(loginFromDTO);
        } else if (PHONE_LOGIN_TYPE.equals(loginFromDTO.getLoginType())) {
            return phoneLogin(loginFromDTO);
        } else {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "登录类型错误");
        }

    }

    @Override
    @Transactional
    public Result register(RegisterFromDTO registerFromDTO) {
        if (RegexUtils.invalidUsername(registerFromDTO.getUsername())) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "用户名不合法，仅支持中英文、数字、下划线、中划线、@及.，4-32长度");
        }
        if (RegexUtils.invalidPassword(registerFromDTO.getPassword())) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "密码不合法，仅中应为、数字、下划线、中划线及特殊符号@.#$%&*!，8-32长度");
        }

        User existUser = query().eq("username", registerFromDTO.getUsername()).one();
        if (existUser != null) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "用户名已存在");
        }

        String encryptPassword = DigestUtils.md5Hex(SALT + registerFromDTO.getPassword());

        User user = new User();
        user.setUsername(registerFromDTO.getUsername());
        user.setPassword(encryptPassword);
        save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname("user_" + RandomUtil.randomString(10));
        userInfoService.save(userInfo);
        return Result.success();

    }

    private Result phoneLogin(LoginFromDTO loginFromDTO) {
        if (RegexUtils.invalidphone(loginFromDTO.getPhone())) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "无效的手机号");
        }

        User existUser = query().eq("phone", loginFromDTO.getPhone()).one();

        if (existUser == null) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "手机号未注册");
        }


        String code = stringRedisTemplate.opsForValue().get(LOGIN_USER_CODE_PREFIX + loginFromDTO.getPhone());

        if (StringUtils.isBlank(code) || !code.equals(loginFromDTO.getCode())) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "验证码错误");
        }


        String token = UUID.randomUUID().toString(true);

        saveInRedis(existUser, token);

        return Result.success(token);

    }

    private void saveInRedis(User existUser, String token) {
        UserDTO userDTO = BeanUtil.toBean(existUser, UserDTO.class);

        stringRedisTemplate.opsForValue().set(LOGIN_USER_TOKEN_PREFIX + token,
                JSONUtil.toJsonStr(userDTO)
        );
        stringRedisTemplate.expire(LOGIN_USER_TOKEN_PREFIX + token, LOGIN_USER_TOKEN_TTL, TimeUnit.MINUTES);
    }

    private Result accountLogin(LoginFromDTO loginFromDTO) {
        if (RegexUtils.invalidUsername(loginFromDTO.getUsername())) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "无效的用户名");
        }
        if (RegexUtils.invalidPassword(loginFromDTO.getPassword())) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "无效的密码");
        }


        String encryptPassword = DigestUtils.md5Hex(SALT + loginFromDTO.getPassword());

        User existUser = query().eq("username", loginFromDTO.getUsername())
                .eq("password", encryptPassword)
                .one();

        if (existUser == null) {
            return Result.error(ErrCode.ERR_LOGIN_ERROR, "用户名或密码错误");
        }

        String token = UUID.randomUUID().toString(true);

        saveInRedis(existUser, token);

        return Result.success(token);
    }
}




