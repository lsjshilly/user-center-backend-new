package com.lsj.usercenter.applicationlistener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lsj.usercenter.mapper.UserMapper;
import com.lsj.usercenter.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.lsj.usercenter.constant.UserConstant.*;


@Component
@Slf4j
public class UserInitApplication implements ApplicationListener<ApplicationReadyEvent> {

    private UserMapper userMapper;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("stat init admin user");

        new Thread(() -> {
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
            queryWrapper.eq("user_account", DEFAULT_ADMIN_USER);
            boolean exists = userMapper.exists(queryWrapper);
            if (exists) {
                log.info("default admin user already exist.");
                return;
            }

            String encryptPassord = DigestUtils.md5Hex(SALT + DEFAULT_ADMIN_PASSWORD);

            User user = new User();
            user.setUsername(DEFAULT_ADMIN_USER);
            user.setUserAccount(DEFAULT_ADMIN_USER);
            user.setUserPassword(encryptPassord);
            user.setAvatar("https://img2.imgtp.com/2024/04/13/l2zEIvky.jpg");
            user.setPhone("182XXXX5932");
            user.setEmail("123456@163.COM");
            user.setGender(0);
            user.setState(0);
            user.setDeleted(0);
            user.setAuthorizator("user::admin");

            int insert = userMapper.insert(user);
            log.info("admin user init result:{}", insert);

        }).start();


    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
