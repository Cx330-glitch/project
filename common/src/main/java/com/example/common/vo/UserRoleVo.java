package com.example.common.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统管理 - 用户角色关联表 查询参数
 */
@Data
@NoArgsConstructor
@ApiModel(description = "系统管理 - 用户角色关联表 查询参数")
public class UserRoleVo {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "用户ids")
    private String userIds;
}
