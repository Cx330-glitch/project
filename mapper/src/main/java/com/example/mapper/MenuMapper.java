package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.entity.History;
import com.example.common.entity.Menu;
import com.example.common.vo.CloudMenuVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT ym.menu_code FROM cloud_menu ym ,cloud_role_menu yrm WHERE ym.id=yrm.m_id AND yrm.r_id=#{0}")
    Set<String> getAllMenus(Long rId);
    @Select("SELECT ym.menu_auth FROM cloud_menu ym ,cloud_role_menu yrm WHERE ym.id=yrm.m_id AND yrm.r_id=#{0}")
    Set<String> getPermission(Long rId);

    @Select("SELECT *  from cloud_menu ym  where ym.parent_id=#{0}")
    List<CloudMenuVo> getMenuListById(long l);

    @Select("select  ym.id,ym.menu_name,ym.parent_id,ym.menu_code from cloud_menu ym,cloud_role_menu  yrm" +
            " where  ym.id=yrm.m_id and  ym.parent_id=#{pId} and yrm.r_id=#{rId} order by ym.id")
    List<CloudMenuVo> getMenuList(@Param("pId") Long pId, @Param("rId") Long rId);

    @Select("select * from cloud_menu ym")
    List<CloudMenuVo> getList();
}