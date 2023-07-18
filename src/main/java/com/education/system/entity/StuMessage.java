package com.education.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
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
@TableName("stu_message")
public class StuMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学号/工号
     */
    private String stuId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 班级号（教师/管理班级号为 0 ）
     */
    private Integer classId;

    /**
     * 密码
     */
    private String password;

    /**
     * 入学年份
     */
    private String enrollment;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 出生日期
     */
    private LocalDateTime birth;

    /**
     * 民族
     */
    private String nation;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 角色权限（#114514# 仅学生操作；#114515 教师操作；#114516# 管理员操作 ）
     */
    private String role;

}
