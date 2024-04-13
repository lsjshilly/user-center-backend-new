package com.lsj.usercenter.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCondition extends User {

    private Date startTime;
    private Date endTime;


    private int current;

    private int pageSize;
}
