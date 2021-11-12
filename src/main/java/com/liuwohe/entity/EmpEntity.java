package com.liuwohe.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;
*/

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

//员工表实体类
@Data
@TableName(value = "tbl_employee")
public class EmpEntity extends Model<EmpEntity> implements Serializable, IExcelModel, IExcelDataModel{
    @TableId(value = "user_id",type = IdType.UUID)
    @Excel(name = "用户编号")
    private String userId;

    @Excel(name = "用户角色")
    @NotNull(message = "用户角色不能为空")
    private String userRole;

    @Excel(name = "区域编号")
    @NotNull(message = "区域编号不能为空")
    private String areaId;

    @Excel(name = "用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @Excel(name = "联系电话（用户初始密码）")
    @NotNull(message = "联系电话不能为空")
    private String phone;

    @TableField(exist = false)
    private AreasEntity area;


    //excel错误信息导出
    @TableField(exist = false)
    private String errorMsg;
    @TableField(exist = false)
    private int rowNum;

//
//
//    //security存储权限认证用的
//    private Collection<? extends GrantedAuthority> authorities;
//    //权限控制
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return phone;
//    }
//    /**
//     * 用户账号是否过期
//     * true: 未过期
//     * false: 已过期
//     * @return
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//    /**
//     * 用户账号是否被锁定
//     * true: 未锁定
//     * false: 锁定
//     * @return
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//    /**
//     * 用户账号凭证(密码)是否过期
//     * 简单的说就是可能会因为修改了密码导致凭证过期这样的场景
//     * true: 过期
//     * false: 无效
//     * @return
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//    /**
//     * 用户账号是否被启用
//     * true: 启用
//     * false: 未启用
//     * @return
//     */
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}