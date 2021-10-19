package com.liuwohe.controller;


import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    EmpService empService;

    @GetMapping("/login")
    public Result test(EmpEntity emp){
        //调用登录方法，验证后返回消息和用户信息
        Result loginRes = empService.login(emp);
        return loginRes;
    }
}
