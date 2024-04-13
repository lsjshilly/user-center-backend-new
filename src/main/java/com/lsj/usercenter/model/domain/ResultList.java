package com.lsj.usercenter.model.domain;


import lombok.Data;

import java.util.List;

@Data
public class ResultList<T> {

    private List<T> items;

    private Long total;
}
