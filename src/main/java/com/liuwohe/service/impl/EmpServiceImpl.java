package com.liuwohe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuwohe.entity.AreasEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.repository.AreasEntityMapper;
import com.liuwohe.repository.EmpEntityMapper;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpServiceImpl implements EmpService{

    @Autowired
    EmpEntityMapper empMapper;
    @Autowired
    AreasEntityMapper areasMapper;

    EmpEntity em = new EmpEntity();
    Result result = new Result();


   /* 根据登录名和密码进行验证,用户名为username，密码为phone*/
    @Override
    public Result login(EmpEntity emp) {
        QueryWrapper<Object> qw = new QueryWrapper<>();
//        根据id查询信息
        qw.eq("username",emp.getUsername());
        //调用实体类的方法查询username
        EmpEntity empEntity = em.selectOne(qw);
        if (empEntity!=null){
//            验证密码
            if(emp.getPhone().equals(empEntity.getPhone())){
                result.setData(empEntity);
                result.setCode("200");
                result.setMsg("登录成功");
                return result;
            }
            result.setCode("400");
            result.setMsg("密码错误，请检查");
            return result;
        }
        result.setCode("400");
        result.setMsg("账号不存在，请检查");
        return result;
    }


    //根据传入的用户名返回用户信息
    public EmpEntity loadUserByUsername(String username) {
        QueryWrapper<Object> qw = new QueryWrapper<>();
        qw.eq("username",username);
        EmpEntity user = em.selectOne(qw);
        if (user == null) {
            System.out.println("不存在该用户!");
        }
        return user;
    }

    //根据传入的id返回员工信息
    @Override
    public EmpEntity getUserById(String id) {
        QueryWrapper<Object> qw = new QueryWrapper<>();
        EmpEntity emp = empMapper.selectById(id);
        AreasEntity area = areasMapper.selectById(emp.getAreaId());
        emp.setArea(area);
        return emp;
    }
}
