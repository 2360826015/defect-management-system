package com.liuwohe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm" )
    private Date date;

    private String areaId;

    private String location;

    private String image;

    private String defectMsg;

    private Integer urgently;

    private String status;

    //维护员工表
    @TableField(exist = false)
    private EmpEntity emp;
    //维护区域表
    @TableField(exist = false)
    private AreasEntity area;
}