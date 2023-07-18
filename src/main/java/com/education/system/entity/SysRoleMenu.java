package com.education.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
@Getter
@Setter
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色权限（#114514# 仅学生操作；#114515 教师操作；#114516# 管理员操作 ）
     */
    private String roleId;

    private Integer menuId;


}
