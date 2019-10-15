package com.likaladi.manager.service;
import com.likaladi.base.BaseService;
import com.likaladi.manager.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * 处理菜单逻辑
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 根据多个角色id查询对应的角色
     * @param roleIds
     * @return
     */
    List<Menu> findByRoles(List<Long> roleIds);

    /**
     * 根据角色id查询对应的菜单列表id
     * @param roleId
     * @return
     */
    Set<Long> findMenuIdsByRoleId(Long roleId);
}
