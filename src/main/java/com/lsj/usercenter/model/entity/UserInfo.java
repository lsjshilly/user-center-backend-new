package com.lsj.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName tb_user_info
 */
@TableName(value = "tb_user_info")
@Data
public class UserInfo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 标签
     */
    private String tags;
    /**
     * 地址
     */
    private String city;
    /**
     * 个人介绍
     */
    private String description;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除
     */
    private Integer deleted;
}