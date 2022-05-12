package com.example.common.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统 - 日志表(History)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@TableName("cloud_history")
public class History extends Model<History>{ //通过实体类自动进行CRUD
    //id
    private Long id;
    //业务名称
    private String serviceName;
    //业务地址
    private String serviceUrl;
    //IP
    private String requestIp;
    //用户id，0:未登录用户操作
    private Long userId;
    //运行状态
    private Integer runStatus;
    //后台运行时间
    private String consumingTime;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //app,pc
    private String serviceType;
    //post,get
    private String httpMethod;
    //浏览器
    private String ua;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
