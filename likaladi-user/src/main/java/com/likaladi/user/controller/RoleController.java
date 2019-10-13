package com.likaladi.user.controller;

import com.likaladi.error.ErrorBuilder;
import com.likaladi.user.dto.RoleDto;
import com.likaladi.user.dto.RolePermissionDto;
import com.likaladi.user.entity.Role;
import com.likaladi.user.service.RolePermissionService;
import com.likaladi.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Api(value = "菜单接口", description = "菜单接口")
@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private RolePermissionService rolePermissionService;

	@ApiOperation(value = "管理后台添加角色", notes = "当前登录用户的菜单", httpMethod = "POST")
//	@LogAnnotation(module = "添加角色")
//	@PreAuthorize("hasAuthority('back:role:save')")
	@PostMapping("/roles")
	public Role save(@RequestBody @Valid RoleDto roleDto) {

		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		roleService.save(role);

		return role;
	}

	@ApiOperation(value = "管理后台删除角色", notes = "管理后台删除角色", httpMethod = "DELETE")
//	@LogAnnotation(module = "删除角色")
//	@PreAuthorize("hasAuthority('back:role:delete')")
	@DeleteMapping("/roles/{id}")
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteById(id);
	}

	@ApiOperation(value = "管理后台修改角色", notes = "管理后台修改角色", httpMethod = "PUT")
//	@LogAnnotation(module = "修改角色")
//	@PreAuthorize("hasAuthority('back:role:update')")
	@PutMapping("/roles")
	public Role update(@RequestBody @Valid RoleDto roleDto) {
		if(Objects.isNull(roleDto.getId())){
			ErrorBuilder.throwMsg("角色id不能为空");
		}
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		role.setUpdateTime(new Date());
		roleService.update(role);

		return role;
	}


	@ApiOperation(value = "管理后台给角色分配权限", notes = "管理后台给角色分配权限", httpMethod = "POST")
//	@LogAnnotation(module = "分配权限")
//	@PreAuthorize("hasAuthority('back:role:permission:set')")
	@PostMapping("/roles/{id}/permissions")
	public void setPermissionToRole(@RequestBody @Valid RolePermissionDto rolePermissionDto) {
		rolePermissionService.setPermissionToRole(rolePermissionDto);
	}
//
//	/**
//	 * 获取角色的权限
//	 *
//	 * @param id
//	 */
//	@PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
//	@GetMapping("/roles/{id}/permissions")
//	public Set<SysPermission> findPermissionsByRoleId(@PathVariable Long id) {
//		return sysRoleService.findPermissionsByRoleId(id);
//	}
//
//	@PreAuthorize("hasAuthority('back:role:query')")
//	@GetMapping("/roles/{id}")
//	public SysRole findById(@PathVariable Long id) {
//		return sysRoleService.findById(id);
//	}
//
//	/**
//	 * 搜索角色
//	 *
//	 * @param params
//	 */
//	@PreAuthorize("hasAuthority('back:role:query')")
//	@GetMapping("/roles")
//	public Page<SysRole> findRoles(@RequestParam Map<String, Object> params) {
//		return sysRoleService.findRoles(params);
//	}

}
