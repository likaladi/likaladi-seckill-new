package com.likaladi.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.user.entity.Role;
import com.likaladi.user.mapper.RoleMapper;
import com.likaladi.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RolerServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult<Role> queryRolesByPage(Map<String, Object> params) {

        PageHelper.startPage(getPage(params),getPage(params));

        Page<Role> page = (Page<Role>) roleMapper.selectByPage(params);

        return new PageResult<>(page.getTotal(), page.getResult(), null);
    }
}
