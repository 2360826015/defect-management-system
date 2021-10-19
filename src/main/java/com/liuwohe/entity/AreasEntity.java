package com.liuwohe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

//地区表实体类
@Data
@TableName(value = "tbl_areas")
public class AreasEntity extends Model<AreasEntity> implements Serializable {
    @TableId(value = "area_id",type = IdType.AUTO)
    private String areaId;

    private String areaName;

}