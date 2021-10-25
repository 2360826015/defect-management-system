package com.liuwohe.entity;


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
    private String userId;

    private String userRole;

    private String areaId;

    private String username;

    private String phone;

    @TableField(exist = false)
    private AreasEntity area;
}