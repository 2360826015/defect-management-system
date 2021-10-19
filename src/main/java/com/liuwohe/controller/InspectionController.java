package com.liuwohe.controller;


import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/inspection")
//巡检人员
public class InspectionController {

    @Autowired
    DefectService defectService;
    @Autowired
    EmpService empService;

    //巡检人员页面接口
    @GetMapping("")
     public Result defect(String id){
        //获取缺陷列表，返回result结果信息
        return defectService.getDefectList(id);
    }

    //缺陷添加接口
    @PostMapping("/defect")
    public Result addDefect(DefectEntity def){
        return  defectService.addorEditDefect(def);
    }

    //缺陷编辑页面（已保存\待复检）
    @PutMapping("/defect")
    public Result editDefect(DefectEntity def){
        return defectService.addorEditDefect(def);
    }

    //缺陷记录删除
    @DeleteMapping("/defect")
    public Result deletDefect(DefectEntity def){
        return defectService.delDefect(def);
    }

    //提交缺陷报告到审核处
    @PostMapping("/send")
    public Result sendDefect(List<DefectEntity> lDefect){
        return defectService.sendDefect(lDefect);
    }

}
