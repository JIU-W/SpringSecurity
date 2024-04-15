package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userid);

}
