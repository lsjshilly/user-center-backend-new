package com.lsj.usercenter.model.request;

import com.lsj.usercenter.model.domain.User;
import lombok.Data;


@Data
public class RegisterRequest extends User {

    private String confirmPassword;

}
