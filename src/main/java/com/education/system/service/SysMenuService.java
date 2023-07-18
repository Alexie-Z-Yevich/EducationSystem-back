package com.education.system.service;

import com.education.system.common.dto.SysMenuDto;
import com.education.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserNav();

    List<SysMenu> tree();
}
