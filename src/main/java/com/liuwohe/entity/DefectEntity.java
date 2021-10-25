package com.liuwohe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;

//缺陷填报表实体类
@Data
@TableName(value = "tbl_defect")
public class DefectEntity extends Model<DefectEntity> implements Serializable {
    @TableId(value = "defect_id",type = IdType.UUID)
    private String defectId;

    private String userId;

    private String phone;

    private Date date;

    private String areaId;

    private String location;

    private String image;

    private String defectMsg;

    private Integer urgently;

    private String status;

    @TableField(exist = false)
    private EmpEntity emp;
}