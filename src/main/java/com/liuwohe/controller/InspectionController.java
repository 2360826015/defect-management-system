package com.liuwohe.controller;


import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/user/inspection")
//巡检人员
public class InspectionController {

    @Autowired
    DefectService defectService;
    @Autowired
    EmpService empService;

    //巡检人员页面接口
    @GetMapping("/{id}")
     public ModelAndView defect(@PathVariable("id") String id, ModelAndView modelAndView){
        //获取缺陷列表，返回result结果信息
        Result result = defectService.getDefectList(id);
        System.out.println(result);
        modelAndView.addObject("result",result);
        //封装返回用户数据
        Result rest=new Result();
        EmpEntity user = empService.getUserById(id);
        rest.setData(user);
        System.out.println(rest);
        modelAndView.addObject("success",rest);
        modelAndView.setViewName("table-inspection");
        return modelAndView;
    }

    //缺陷添加
    @PostMapping("/defect")
    public Result addDefect(DefectEntity def,@RequestParam("filepath") MultipartFile defImage) throws IOException {
        Result result = defectService.addorEditDefect(def, defImage);
        System.out.println(result);
        return result;
    }

    //缺陷修改页面（已保存\待复检）
    @PutMapping("/defect")
    public Result editDefect(DefectEntity def,@RequestParam("filepath") MultipartFile defImage) throws IOException {
        System.out.println("文件名"+defImage.getOriginalFilename());
        return defectService.addorEditDefect(def,defImage);
    }

    //缺陷记录删除(同时删除上传的图片文件)
    @DeleteMapping("/defect/{id}")
    public Result deletDefect(@PathVariable("id") String id){
        System.out.println("获取id:"+id);
        return defectService.delDefect(id);
    }

    //提交缺陷报告到审核处
    @PostMapping("/send/{id}")
    public Result sendDefect(@PathVariable("id") String id){
        System.out.println("缺陷报告单id"+id);
        return defectService.sendDefect(id);
    }

    //完成复检，更新缺陷记录状态
    @PutMapping("/defect/finish/{id}")
    public Result finishDefect(@PathVariable("id") String id){
        System.out.println("传入id:"+id+"执行后结果为："+defectService.finishDefect(id));
        return defectService.finishDefect(id);
    }
}
