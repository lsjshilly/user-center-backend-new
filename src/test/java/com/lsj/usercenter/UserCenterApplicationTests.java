package com.lsj.usercenter;

import com.lsj.usercenter.model.domain.User;
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
        user.setUserAccount("zhangsanfeng");
        user.setUserPassword("12345678");
        user.setAvatar("http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg");
        user.setEmail("abc.163.com");
        user.setGender(1);
        user.setState(0);
        user.setDeleted(0);


        userService.save(user);


        List<User> list = userService.list();
        Assertions.assertFalse(list.isEmpty());
    }

}
