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

@Controller
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
        modelAndView.addObject("result",result);
        //封装返回用户数据
        Result rest=new Result();
        EmpEntity user = empService.getUserById(id);
        rest.setData(user);
        modelAndView.addObject("success",rest);
        modelAndView.setViewName("tab-panel");
        return modelAndView;
    }

    //缺陷添加
    @PostMapping("/defect")
    public Result addDefect(DefectEntity def,@RequestParam("pic") MultipartFile file) throws IOException {
        return  defectService.addorEditDefect(def,file);
    }

    //缺陷修改页面（已保存\待复检）
    @PutMapping("/defect")
    public Result editDefect(DefectEntity def,@RequestParam("pic") MultipartFile file) throws IOException {
        return defectService.addorEditDefect(def,file);
    }

    //缺陷记录删除(待完成：同时删除上传的图片文件)
    @DeleteMapping("/defect")
    public Result deletDefect(DefectEntity def){
        return defectService.delDefect(def);
    }

    //提交缺陷报告到审核处
    @PostMapping("/send")
    public Result sendDefect(DefectEntity def){
        return defectService.sendDefect(def);
    }

}
