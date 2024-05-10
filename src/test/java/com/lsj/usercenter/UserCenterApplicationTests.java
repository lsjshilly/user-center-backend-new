package com.lsj.usercenter;

import com.lsj.usercenter.model.entity.User;
import com.lsj.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void testSearchUser() {
        User user = new User();
        user.setUsername("张三丰");
        user.setUsername("zhangsanfeng");
        user.setPassword("12345678");
        user.setEmail("abc.163.com");
        user.setState(0);
        user.setDeleted(0);


        userService.save(user);


        List<User> list = userService.list();
        Assertions.assertFalse(list.isEmpty());
    }

}
