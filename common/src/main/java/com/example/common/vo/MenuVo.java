package com.example.common.vo;
import com.example.common.entity.Menu;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
/**
 *  菜单
 */
@Data
@NoArgsConstructor
public class MenuVo extends Menu implements Serializable {
    List<MenuVo> children = Lists.newArrayList();


}
