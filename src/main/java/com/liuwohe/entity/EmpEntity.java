package com.liuwohe.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

//员工表实体类
@Data
@TableName(value = "tbl_employee")
public class EmpEntity extends Model<EmpEntity> implements Serializable {
    @TableId(value = "user_id",type = IdType.UUID)
    @Excel(name = "用户编号")
    private String userId;

    @Excel(name = "用户角色")
    private String userRole;

    @Excel(name = "区域编号")
    private String areaId;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "联系电话（用户初始密码）")
    private String phone;

    @TableField(exist = false)
    private AreasEntity area;
}