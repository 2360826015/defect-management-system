package com.liuwohe.controller;


import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.DefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
//审核人员
public class CensorController {

    @Autowired
    DefectService defectService;

    //审核人员页面，返回缺陷列表展示
    @GetMapping("/censor")
    public Result examine(@PathVariable("id")String id){
        Result result = defectService.getDefectList(id);
        return result;
    }
}
