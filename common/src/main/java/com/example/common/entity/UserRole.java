package com.example.common.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统 - 用户-角色(UserRole)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@TableName("cloud_user_role")
public class UserRole extends Model<UserRole>{
    //主键
    private Long id;
    //角色ID
    private Long roleId;
    //用户ID
    private Long userId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //创建者
    private String createUser;

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
