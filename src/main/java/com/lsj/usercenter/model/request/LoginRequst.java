package com.lsj.usercenter.model.request;


import lombok.Data;

@Data
public class LoginRequst {

    private String userAccount;

    private String userPassword;
}
