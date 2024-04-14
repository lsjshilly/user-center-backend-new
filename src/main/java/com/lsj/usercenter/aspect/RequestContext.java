package com.lsj.usercenter.aspect;

import com.lsj.usercenter.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestContext {

    private HttpServletRequest request;


    private User user;


}
