package com.lsj.usercenter.model.request;

import com.lsj.usercenter.model.domain.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest extends User {

    private String confirmPassword;

}
