package com.education.system.controller;


import cn.hutool.core.map.MapUtil;
import com.education.system.common.dto.SysMenuDto;
import com.education.system.common.lang.Result;
import com.education.system.entity.StuMessage;
import com.education.system.entity.SysMenu;
import com.education.system.service.StuMessageService;
import com.education.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    StuMessageService stuMessageService;

    @Autowired
    SysMenuService sysMenuService;

    /***
     * 获取当前用户的菜单和权限信息
     * @param principal
     * @return
     */
    @GetMapping("/nav")
    public Result nav(Principal principal) {
        StuMessage stuMessage = stuMessageService.getByStuId(principal.getName());

        // 获取权限信息
        String authorityInfo = stuMessageService.getUserAuthorityInfo(stuMessage.getId());
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        // 获取导航栏信息
        List<SysMenuDto> navs = sysMenuService.getCurrentUserNav();

        return Result.succ(MapUtil.builder()
                .put("authorities", authorityInfoArray)
                .put("nav", navs)
                .map());
    }

    @GetMapping("/list")
    public Result list() {
        List <SysMenu> menus = sysMenuService.tree();
        return Result.succ(menus);
    }

}
