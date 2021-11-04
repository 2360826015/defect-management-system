package com.liuwohe.controller;


import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user/manger")
public class MangerController {

    @Autowired
    DefectService defectService;
    @Autowired
    EmpService empService;

    @GetMapping("/statistics/{id}")
    public ModelAndView getAllList(@PathVariable("id")String id, ModelAndView modelAndView){
        //封装返回用户数据
        Result rest=new Result();
        EmpEntity user = empService.getUserById(id);
        rest.setData(user);
        modelAndView.addObject("success",rest);
        //查询所有缺陷数据并返回
        Result result = defectService.getAll();
        modelAndView.addObject("result",result);
        modelAndView.setViewName("table-manger");
        return modelAndView;
    }
}
