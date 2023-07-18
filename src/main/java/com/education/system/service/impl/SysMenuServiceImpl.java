package com.education.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.system.common.dto.SysMenuDto;
import com.education.system.entity.StuMessage;
import com.education.system.entity.SysMenu;
import com.education.system.mapper.StuMessageMapper;
import com.education.system.mapper.SysMenuMapper;
import com.education.system.service.StuMessageService;
import com.education.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    StuMessageService stuMessageService;

    @Autowired
    StuMessageMapper stuMessageMapper;

    @Override
    public List<SysMenuDto> getCurrentUserNav() {
        String stuId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StuMessage stuMessage = stuMessageService.getByStuId(stuId);

        List<Long> menuIds = stuMessageMapper.getNavMenuIds(stuMessage.getId());

        List<SysMenu> menus = this.listByIds(menuIds);

        // 转树状结构
        List<SysMenu> menuTree = buildTreeMenu(menus);

        // 实体转DTO
        return convert(menuTree);
    }

    @Override
    public List<SysMenu> tree() {
        // 获取所有菜单信息
        List<SysMenu> sysMenus = this.list(new QueryWrapper<SysMenu>().orderByAsc("orderNum"));

        // 转成树状结构
        return buildTreeMenu(sysMenus);
    }

    private List<SysMenuDto> convert(List<SysMenu> menuTree) {
        List<SysMenuDto> menuDtos = new ArrayList<>();

        menuTree.forEach(m -> {
            SysMenuDto dto = new SysMenuDto();

            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setIcon(m.getIcon());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());

            if (m.getChildren().size() > 0) {

                // 子节点调用当前方法进行再次转换
                dto.setChildren(convert(m.getChildren()));
            }

            menuDtos.add(dto);
        });

        return menuDtos;
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {

        List<SysMenu> finalMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (SysMenu menu : menus) {

            for (SysMenu e : menus) {
                if (menu.getId() == e.getParentId()) {
                    menu.getChildren().add(e);
                }
            }

            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }

        return finalMenus;
    }

}
