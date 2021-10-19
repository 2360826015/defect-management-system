package com.liuwohe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//巡检日志表实体类
@Data
@TableName(value = "tbl_inspection_log")
public class InspEntity extends Model<InspEntity> implements Serializable {
    @TableId(value = "log_id",type = IdType.UUID)
    private String logId;

    private String userId;

    private String areaId;

    private String phone;

    private Date date;

    private String complete;

}