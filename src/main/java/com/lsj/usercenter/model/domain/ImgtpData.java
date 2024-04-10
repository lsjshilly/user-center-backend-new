package com.lsj.usercenter.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgtpData {
    private String token;

    private String name;

    private String url;
}
