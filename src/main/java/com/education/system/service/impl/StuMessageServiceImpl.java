package com.education.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.system.entity.StuMessage;
import com.education.system.entity.SysMenu;
import com.education.system.mapper.StuMessageMapper;
import com.education.system.service.StuMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.system.service.SysMenuService;
import com.education.system.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
@Service
public class StuMessageServiceImpl extends ServiceImpl<StuMessageMapper, StuMessage> implements StuMessageService {

    @Autowired
    StuMessageMapper stuMessageMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public StuMessage getByStuId(String name) {
        return getOne(new QueryWrapper<StuMessage>().eq("name", name));
    }

    @Override
    public String getUserAuthorityInfo(Long id) {

        StuMessage stuMessage = stuMessageMapper.selectById(id);

        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + stuMessage.getStuId())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + stuMessage.getStuId());
        } else {

            // 获取菜单操作编码
            List<Long> menuIds = stuMessageMapper.getNavMenuIds(id);
            if (menuIds.size() > 0) {
                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(SysMenu::getPerms).collect(Collectors.joining(","));
                authority += "," + menuPerms;
            }

            redisUtil.set("GrantedAuthority:" + stuMessage.getStuId(), authority, 60 * 60);
        }

        return authority;

    }
}
