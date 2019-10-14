package com.likaladi.user.service.impl;

import com.github.pagehelper.Page;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.user.dto.RoleConditionDto;
import com.likaladi.user.entity.Role;
import com.likaladi.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RolerServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Override
    public Page<Role> queryRolesByPage(RoleConditionDto conditionDto) {
        return null;
    }
}
