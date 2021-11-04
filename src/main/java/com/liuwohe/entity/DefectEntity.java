package com.liuwohe.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

//缺陷填报表实体类
@Data
@TableName(value = "tbl_defect")
public class DefectEntity extends Model<DefectEntity> implements Serializable {
    @TableId(value = "defect_id",type = IdType.UUID)
    private String defectId;

    @Excel(name = "填报人编号")
    private String userId;

    @Excel(name = "填报人联系电话")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm" )
    @Excel(name = "填报日期",exportFormat = "yyyy-MM-dd",format = "yyyy-MM-dd",databaseFormat = "yyyyMMdd")
    @NotNull(message = "填报日期不能为空!")
    private Date date;

    @Excel(name = "区域编号")
    @NotNull(message = "所属区域不能为空")
    private String areaId;

    @Excel(name = "详细定位")
    private String location;

    @Excel(name = "图片路径")
    private String image;

    @Excel(name = "缺陷详细描述")
    private String defectMsg;

    @Excel(name = "紧急程度")
    private Integer urgently;

    @Excel(name = "缺陷状态")
    private String status;

    //维护员工表
    @TableField(exist = false)
    private EmpEntity emp;
    //维护区域表
    @TableField(exist = false)
    private AreasEntity area;
}