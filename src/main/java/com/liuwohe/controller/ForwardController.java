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
    //封装返回结果工具
    Result result=new Result();

    //跳转到主页面
    @GetMapping("/index/{id}")
    public ModelAndView toIndex(@PathVariable("id") String id,ModelAndView modelAndView){
        System.out.println("返回接收的id:"+id);
        //获取用户信息
        EmpEntity user = empService.getUserById(id);
        //封装返回结果
        result.setData(user);
        //        获取统计信息
        Result s = defectService.getStatistics(id);
        System.out.println("返回的统计数据:"+s);
//        设置返回信息
        modelAndView.addObject("statistics",s);
        modelAndView.addObject("success",result);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    //跳转到数据统计页面
    @GetMapping("/statistics/{id}")
    public ModelAndView toStatistics(@PathVariable("id") String id,ModelAndView modelAndView){
        //获取用户信息
        EmpEntity user = empService.getUserById(id);
        //封装返回结果
        result.setData(user);
        //        获取统计信息
        Result s = defectService.getStatistics(id);
        System.out.println("返回的统计数据:"+s);
//        设置返回信息
        modelAndView.addObject("statistics",s);
        modelAndView.addObject("success",result);
        modelAndView.setViewName("statistics");
        return modelAndView;
    }



    //跳转到缺陷添加页面
    @GetMapping("/defect/{id}")
    public ModelAndView toDefectAddPage(@PathVariable("id") String id, ModelAndView modelAndView){
//        返回员工信息
        EmpEntity user = empService.getUserById(id);
        result.setData(user);
        modelAndView.addObject("success",result);
        modelAndView.setViewName("form");
        return modelAndView;
    }
    //跳转到缺陷记录编辑页面（已保存\已驳回\待审核），根据缺陷报告的id返回数据
    @GetMapping("/edit/{id}")
    public ModelAndView toDefectEditPage(@PathVariable("id") String id,EmpEntity emp,ModelAndView modelAndView){
//        根据id返回缺陷表单数据
        Result result = defectService.getDefectById(id);
        System.out.println("结果输出"+result.getMsg());
        modelAndView.addObject("success",result);
        //审核页面跳转
        if ("获取待审核数据成功".equals(result.getMsg())){
            modelAndView.addObject("userId",emp.getUserId());
            modelAndView.setViewName("edit-censor");
            return modelAndView;
        }
        //巡检页面跳转
        modelAndView.setViewName("edit");
        return modelAndView;
    }
}
