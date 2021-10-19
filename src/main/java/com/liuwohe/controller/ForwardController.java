package com.liuwohe.controller;

import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ForwardController {

    @Autowired
    EmpService empService;


    //跳转到缺陷添加页面
    @GetMapping("/inspection/defect")
    public EmpEntity toDefectAddPage(@PathVariable("id")String id){
//        返回员工信息
        return empService.getUserById(id);
    }
    //跳转到缺陷记录编辑页面（已保存）
    @GetMapping("/")
    public Result toDefectEditPage(){

        return new Result();
    }
}
