package com.example.common.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统 - 角色 - 菜单(RoleMenu)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@TableName("cloud_role_menu")
public class RoleMenu extends Model<RoleMenu>{
    //主键
    private Long id;
    //角色ID
    private Long rId;
    //菜单ID
    private Long mId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
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
