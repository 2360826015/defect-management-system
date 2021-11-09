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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

//地区表实体类
@Data
@TableName(value = "tbl_areas")
public class AreasEntity extends Model<AreasEntity> implements Serializable, IExcelModel, IExcelDataModel {
    @TableId(value = "area_id")
    @Excel(name = "区域编号")
    @NotNull(message = "区域编号不能为空")
    private String areaId;

    @Excel(name = "区域名称")
    @NotNull(message = "区域名称不能为空")
    private String areaName;

    //excel错误信息导出
    @TableField(exist = false)
    private String errorMsg;
    @TableField(exist = false)
    private int rowNum;
}