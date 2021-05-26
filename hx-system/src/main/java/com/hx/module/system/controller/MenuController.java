package com.hx.module.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.hx.annotation.Log;
import com.hx.exception.HxException;
import com.hx.module.security.domain.dto.CommonResultDTO;
import com.hx.module.system.domain.dto.MenuDTO;
import com.hx.module.system.domain.entity.Menu;
import com.hx.module.system.domain.mapstruct.MenuMapper;
import com.hx.module.system.domain.query.MenuQuery;
import com.hx.module.system.service.MenuService;
import com.hx.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@Api(tags = "系统：菜单管理")
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuServiceImpl;
    private final MenuMapper menuMapperImpl;
    private static final String ENTITY_NAME = "menu";

    public MenuController(MenuService menuServiceImpl, MenuMapper menuMapperImpl) {
        this.menuServiceImpl = menuServiceImpl;
        this.menuMapperImpl = menuMapperImpl;
    }


    @Log("导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, MenuQuery criteria) throws Exception {
        menuServiceImpl.download(menuServiceImpl.queryAll(criteria, false), response);
    }

    @GetMapping(value = "/user")
    @ApiOperation("获取前端所需菜单")
    public CommonResultDTO<Object> buildMenus( @RequestParam String userid){
        List<MenuDTO> menuDtoList = menuServiceImpl.findByUser(Long.parseLong(userid));
        List<MenuDTO>  menuDtos = menuServiceImpl.buildTree(menuDtoList);
        return new CommonResultDTO<>(menuDtos);
    }


//    @ApiOperation("返回全部的菜单")
//    @GetMapping(value = "/initdata")
//    public CommonResultDTO<Object> initdata(@RequestParam Long userid){
//        // @PreAuthorize("@hxPermissionConfig.check('menu:list','roles:list')")
//        //return new ResponseEntity<>(menuServiceImpl.getMenus(userid),HttpStatus.OK);
//        System.out.println(userid);
//        List<MenuDTO> menuDtoList = menuServiceImpl.findByUser(userid);
//        List<MenuDTO>  menuDtos = menuServiceImpl.buildTree(menuDtoList);
//        return new CommonResultDTO(menuDtos);
//
//    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping(value="/query")
    public ResponseEntity<Object> query(MenuQuery criteria) throws Exception {
        List<MenuDTO> menuDtoList = menuServiceImpl.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtil.toPage(menuDtoList, menuDtoList.size()),HttpStatus.OK);
    }


    @Log("查询菜单")
    @ApiOperation("查询菜单:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Set<MenuDTO> menuDtos = new LinkedHashSet<>();
        if(CollectionUtil.isNotEmpty(ids)){
            for (Long id : ids) {
                MenuDTO menuDto = menuServiceImpl.findById(id);
                menuDtos.addAll(menuServiceImpl.getSuperior(menuDto, new ArrayList<>()));
            }
            return new ResponseEntity<>(menuServiceImpl.buildTree(new ArrayList<>(menuDtos)),HttpStatus.OK);
        }
        return new ResponseEntity<>(menuServiceImpl.getMenus(null),HttpStatus.OK);
    }

    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping(value = "add")
    public ResponseEntity<Object> create(@Validated @RequestBody Menu resources){
        if (resources.getId() != null) {
            throw new HxException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        menuServiceImpl.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping(value = "update")
    public ResponseEntity<Object> update(@Validated(Menu.Update.class) @RequestBody Menu resources){
        menuServiceImpl.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping(value = "delete")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        Set<Menu> menuSet = new HashSet<>();
        for (Long id : ids) {
            List<MenuDTO> menuList = menuServiceImpl.getMenus(id);
            menuSet.add(menuServiceImpl.findOne(id));
            menuSet = menuServiceImpl.getDeleteMenus(menuMapperImpl.toEntity(menuList), menuSet);
        }
        menuServiceImpl.delete(menuSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
