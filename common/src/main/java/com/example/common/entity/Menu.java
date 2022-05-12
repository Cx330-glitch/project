package com.example.common.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统-菜单表 (Menu)表实体类
 *
 */

@SuppressWarnings("serial")
@Data
@TableName("cloud_menu")
public class Menu extends Model<Menu>{
    //主键
    private Long id;
    //pid
    private Long parentId;
    //url
    private String menuPath;
    //权限定义
    private String menuAuth;
    //权限名称
    private String menuName;
    //排序
    private Integer menuSort;
    //资源图标
    private String menuIcon;
    //种类 menu、button
    private String menuType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //是否启用
    private String isEnable;
    //创建者

    private Date createUser;
    //describe
    private String describeText;
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
