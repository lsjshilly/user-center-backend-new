package com.lsj.usercenter.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgTPResult {
    private String code;

    private String msg;

    private long time;

    private ImgtpData data;
}