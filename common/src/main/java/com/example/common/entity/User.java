package com.example.common.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统-用户表(User)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@TableName("cloud_user")
public class User extends Model<User>{
    //id
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //账号
    private String accountName;
    //密码
    private String passWord;
    //姓名
    private String realName;
    //0:男 1:女
    private String sex;
    //手机
    private String tel;
    //Email
    private String email;
    //头像
    private String photo;
    //是否锁定
    private String isLock;
    //盐值
    private String pwdSalt;
    //token
    private String loginToken;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //创建者
    private String createUser;
    //最后登录时间
    private Date lastLoginTime;
    //更新者
    private String updateUser;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
