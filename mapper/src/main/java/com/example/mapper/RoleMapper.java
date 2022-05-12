package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.Role;
import com.example.common.vo.RoleVo;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT *,id roleId from cloud_role")
    List<RoleVo> getList();


}