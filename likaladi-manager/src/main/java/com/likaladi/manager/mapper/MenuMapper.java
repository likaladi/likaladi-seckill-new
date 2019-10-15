package com.likaladi.manager.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.manager.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface MenuMapper extends CommonMapper<Menu> {

    @Select({
            "<script>"
                    + "select t.id, t.parent_id parentId, t.name, t.url, t.css, t.sort,"
                    + "t.create_time createTime, t.update_time updateTime from role_menu r"
                    + " LEFT JOIN menu t on r.menuId = t.id"
                    + " WHERE r.roleId in "
                    + "<foreach item='roleId' index='index' collection='roleIds' open='(' separator=',' close=')'>"
                    + "#{roleId}"
                    + "</foreach>"
            + "</script>"
    })
    List<Menu> findByRoles(@Param("roleIds") List<Long> roleIds);

    @Select("select m.id from role_menu rm left join menu m on (rm.menu_id = m.id) where rm.role_id = #{roleId}")
    Set<Long> findMenuIdsByRoleId(@Param("roleId") Long roleId);
}