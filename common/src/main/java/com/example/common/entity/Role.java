package com.example.common.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统-角色表 (Role)表实体类

 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@TableName("cloud_role")
public class Role extends Model<Role> {
    //主键ID
    private Long id;
    //角色英文
    private String roleEn;
    //角色中文
    private String roleCn;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //描述
    private String describeText;
    //是否启用
    private String isEnable;
    //系统用户
    private String readonly;
    //创建者
    private String createUser;
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
