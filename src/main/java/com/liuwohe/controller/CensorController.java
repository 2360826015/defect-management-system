package com.liuwohe.controller;


import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user/censor")
//审核人员
public class CensorController {

    @Autowired
    DefectService defectService;
    @Autowired
    EmpService empService;

    //审核人员页面，返回缺陷列表展示
    @GetMapping("/{id}")
    public ModelAndView examine(@PathVariable("id") String id, ModelAndView modelAndView){
        //根据用户id返回缺陷列表
        Result result = defectService.getDefectList(id);
        modelAndView.addObject("result",result);
        //封装返回用户数据
        Result rest=new Result();
        EmpEntity user = empService.getUserById(id);
        rest.setData(user);
        modelAndView.addObject("success",rest);
//        设置目标页面
        modelAndView.setViewName("table");
        return modelAndView;
    }

    //返回缺陷已通过审核的缺陷问题列表展示
    @GetMapping("/finish/{id}")
    public ModelAndView finishExamine(@PathVariable("id") String id, ModelAndView modelAndView){
        //根据用户id返回缺陷列表
        Result result = defectService.getDefectList(id);
        modelAndView.addObject("result",result);
        //封装返回用户数据
        Result rest=new Result();
        EmpEntity user = empService.getUserById(id);
        rest.setData(user);
        modelAndView.addObject("success",rest);
//        设置目标页面
        modelAndView.setViewName("table-finish");
        return modelAndView;
    }

    //接收前端用户传入的缺陷表单id，执行复检任务发布方法
    @PostMapping("/finish/{id}")
    public ModelAndView Reinspection (@PathVariable("id") String id, EmpEntity emp, ModelAndView modelAndView){
        defectService.reinspection(id);
        modelAndView.setViewName("redirect:/user/censor/finish/"+emp.getUserId());
        return modelAndView;
    }


    //查看缺陷报告，评定缺陷紧急程度，审核缺陷问题报告
    @PostMapping("/defect")
    public Result censorDefect(DefectEntity def){
        System.out.println("Urgently更新数据为:"+def.getUrgently());
        return defectService.censorDefect(def);
    }
}

