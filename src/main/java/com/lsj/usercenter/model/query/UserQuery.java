package com.lsj.usercenter.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {

    private String userId;

    private String username;

    private String nickname;

    private Short gender;

    private List<String> tags;

    private Integer pageSize;

    private Integer pageNum = 1;

}
