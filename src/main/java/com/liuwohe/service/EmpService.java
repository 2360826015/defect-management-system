package com.liuwohe.service;

import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;

public interface EmpService {
//    登录验证方法
    Result login(EmpEntity emp);
    //获取用户信息
    EmpEntity loadUserByUsername(String username);
    //根据传入的id返回员工信息
    EmpEntity getUserById(String id);
}
