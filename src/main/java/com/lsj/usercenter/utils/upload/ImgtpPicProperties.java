package com.lsj.usercenter.utils.upload;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "imgtp.pic")
@Data
public class ImgtpPicProperties {

    private String email;

    private String password;
}
