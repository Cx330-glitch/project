package com.example.common.vo;
import com.example.common.entity.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CloudMenuVo extends Menu {
    private List<CloudMenuVo> children;
    private List<CloudMenuVo> menuList;
}
