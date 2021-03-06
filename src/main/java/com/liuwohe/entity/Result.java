package com.liuwohe.entity;

import lombok.Data;

import java.io.Serializable;

//数据返回类
@Data
public class Result implements Serializable {
    private String code;
    private String msg;
    private Object data;

    public static Result succ(Object data){
        Result m=new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg("操作成功");
        return m;
    }

    public static Result succ(Object data,String msg){
        Result m=new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg(msg);
        return m;
    }

    public static Result fail(String msg){
        Result m=new Result();
        m.setCode("-1");
        m.setData(null);
        m.setMsg(msg);
        return m;
    }

    public static Result fail(Object data,String msg){
        Result m=new Result();
        m.setCode("-1");
        m.setData(data);
        m.setMsg(msg);
        return m;
    }
}
