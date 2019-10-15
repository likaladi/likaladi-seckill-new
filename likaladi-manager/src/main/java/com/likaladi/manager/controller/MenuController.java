package com.likaladi.manager.controller;

import com.likaladi.auth.util.AuthUtil;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.manager.dto.MenuDto;
import com.likaladi.manager.entity.Menu;
import com.likaladi.manager.service.MenuService;
import com.likaladi.user.model.UserAuth;
import com.likaladi.user.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "菜单接口", description = "菜单接口")
@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 当前登录用户的菜单
	 *
	 * @return
	 */
	@ApiOperation(value = "当前登录用户的菜单", notes = "当前登录用户的菜单", httpMethod = "GET")
	@GetMapping("/me")
	public List<Menu> findMyMenu() {

		UserAuth userAuth = AuthUtil.getLoginUserInfo();
		List<RoleVo> roleVos = userAuth.getRoles();
		if (CollectionUtils.isEmpty(roleVos)) {
			return Collections.emptyList();
		}

		/** 根据roleId 将roleVos集合 转换对应的roleId列表  */
		List<Long> roleIds = roleVos.stream().map(RoleVo::getRoleId).collect(Collectors.toList());

		/** 根据多个角色id查询对应的菜单 */
		List<Menu> menus = menuService.findByRoles(roleIds);

		/** 根据menuId去重 */
		List<Menu> newMenus = menus.stream().collect(
				Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Menu::getId))), ArrayList::new));


		/** 过滤出parentId等于0 的菜单menu*/
		List<Menu> firstLevelMenus = newMenus.stream().filter(m -> m.getParentId().equals(0L))
				.collect(Collectors.toList());

		firstLevelMenus.forEach(m -> {
			setChild(m, newMenus);
		});

		return firstLevelMenus;
	}

	private void setChild(Menu menu, List<Menu> newMenus) {
		/** 从menuslist中过滤出 对应menu下的所有子类菜单 */
		List<Menu> child = newMenus.stream().filter(m -> m.getParentId().equals(menu.getId()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(child)) {
			menu.setChild(child);
			// 2018.06.09递归设置子元素，多级菜单支持
			child.parallelStream().forEach(c -> {
				setChild(c, newMenus);
			});
		}
	}

//	/**
//	 * 给角色分配菜单
//	 *
//	 * @param roleId  角色id
//	 * @param menuIds 菜单ids
//	 */
//	@LogAnnotation(module = "分配菜单")
//	@PreAuthorize("hasAuthority('back:menu:set2role')")
//	@PostMapping("/toRole")
//	public void setMenuToRole(Long roleId, @RequestBody Set<Long> menuIds) {
//		menuService.setMenuToRole(roleId, menuIds);
//	}
//
	/**
	 *
	 */
	@ApiOperation(value = "菜单树ztree接口", notes = "菜单树ztree接口", httpMethod = "GET")
//	@PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
	@GetMapping("/tree")
	public List<Menu> findMenuTree() {
		List<Menu> all = menuService.findAll();
		List<Menu> list = new ArrayList<>();
		setMenuTree(0L, all, list);
		return list;
	}

	/**
	 * 菜单树
	 *
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setMenuTree(Long parentId, List<Menu> all, List<Menu> list) {
		all.forEach(menu -> {
			if (parentId.equals(menu.getParentId())) {
				list.add(menu);

				List<Menu> child = new ArrayList<>();
				menu.setChild(child);
				setMenuTree(menu.getId(), all, child);
			}
		});
	}


	@ApiOperation(value = "获取角色的菜单ids", notes = "获取角色的菜单ids", httpMethod = "GET")
//	@PreAuthorize("hasAnyAuthority('back:menu:set2role','menu:byroleid')")
	@GetMapping("/byRole/{roleId}")
	public Set<Long> findMenuIdsByRoleId(@PathVariable Long roleId) {
		return menuService.findMenuIdsByRoleId(roleId);
	}


	@ApiOperation(value = "添加菜单", notes = "添加菜单", httpMethod = "POST")
//	@LogAnnotation(module = "添加菜单")
//	@PreAuthorize("hasAuthority('back:menu:save')")
	@PostMapping
	public Menu save(@RequestBody @Valid MenuDto menuDto) {
		Menu menu = Menu.builder().build();
		BeanUtils.copyProperties(menuDto,menu);
		menuService.save(menu);
		return menu;
	}

	/**
	 * 修改菜单
	 *
	 * @param menuDto
	 */
	@ApiOperation(value = "修改菜单", notes = "修改菜单", httpMethod = "PUT")
//	@LogAnnotation(module = "修改菜单")
//	@PreAuthorize("hasAuthority('back:menu:update')")
	@PutMapping
	public Menu update(@RequestBody @Valid MenuDto menuDto) {
		if(Objects.isNull(menuDto.getId())){
			ErrorBuilder.throwMsg("菜单id不能为空");
		}
		Menu menu = Menu.builder().build();
		BeanUtils.copyProperties(menuDto, menu);
		menuService.update(menu);
		return menu;
	}

	@ApiOperation(value = "删除菜单", notes = "删除菜单", httpMethod = "DELETE")
//	@LogAnnotation(module = "删除菜单")
//	@PreAuthorize("hasAuthority('back:menu:delete')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		menuService.deleteById(id);
	}

	@ApiOperation(value = "查询所有菜单", notes = "查询所有菜单", httpMethod = "GET")
//	@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/all")
	public List<Menu> findAll() {
		List<Menu> all = menuService.findAll();
		List<Menu> list = new ArrayList<>();
		setSortTable(0L, all, list);

		return list;
	}

	/**
	 * 菜单table
	 *
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setSortTable(Long parentId, List<Menu> all, List<Menu> list) {
		all.forEach(a -> {
			if (a.getParentId().equals(parentId)) {
				list.add(a);
				setSortTable(a.getId(), all, list);
			}
		});
	}

	@ApiOperation(value = "根据id查询菜单", notes = "根据id查询菜单", httpMethod = "GET")
//	@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/{id}")
	public Menu findById(@PathVariable Long id) {
		return menuService.findById(id);
	}

}
