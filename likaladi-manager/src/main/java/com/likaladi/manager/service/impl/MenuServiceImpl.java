package com.likaladi.manager.service.impl;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.manager.entity.Menu;
import com.likaladi.manager.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.likaladi.manager.service.MenuService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findByRoles(List<Long> roleIds) {
        return menuMapper.findByRoles(roleIds);
    }

    @Override
    public Set<Long> findMenuIdsByRoleId(Long roleId) {
        return menuMapper.findMenuIdsByRoleId(roleId);
    }

}
