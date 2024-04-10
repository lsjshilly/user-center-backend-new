package com.lsj.usercenter.model.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgtpResponse {
    private String code;

    private String msg;

    private long time;

    private ImgtpData data;
}

