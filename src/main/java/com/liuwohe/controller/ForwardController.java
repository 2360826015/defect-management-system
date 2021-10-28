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
@RequestMapping("/user/inspection")
public class ForwardController {

    @Autowired
    EmpService empService;
    @Autowired
    DefectService defectService;

    //跳转到主页面
    @GetMapping("/index/{id}")
    public ModelAndView toIndex(@PathVariable("id") String id,ModelAndView modelAndView){
        Result result=new Result();
        //获取用户信息
        EmpEntity user = empService.getUserById(id);
        //封装返回结果
        result.setData(user);
//        设置返回信息
        modelAndView.addObject("success",result);
        modelAndView.setViewName("index");
        return modelAndView;
    }


    //跳转到缺陷添加页面
    @GetMapping("/defect/{id}")
    public ModelAndView toDefectAddPage(@PathVariable("id") String id, ModelAndView modelAndView){
//        返回员工信息
        EmpEntity user = empService.getUserById(id);
        Result result=new Result();
        result.setData(user);
        modelAndView.addObject("success",result);
        modelAndView.setViewName("form");
        return modelAndView;
    }
    //跳转到缺陷记录编辑页面（已保存\已驳回），根据缺陷报告的id返回数据
    @GetMapping("/edit/{id}")
    public ModelAndView toDefectEditPage(@PathVariable("id") String id,ModelAndView modelAndView){
        Result result = defectService.getDefectById(id);
        System.out.println("结果输出"+result.getMsg());
        modelAndView.addObject("success",result);
        if ("获取待审核数据成功".equals(result.getMsg())){
            modelAndView.setViewName("edit-censor");
            return modelAndView;
        }
        modelAndView.setViewName("edit");
        return modelAndView;
    }
}
