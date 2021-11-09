package com.liuwohe.controller;

import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.EasyPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PoiController {

    @Autowired
    EasyPoiService easyPoiService;

    //所有缺陷表格导出
    @GetMapping("/download")
    @ResponseBody
    public void downExcel(HttpServletResponse resp) throws IOException {
        //根据条件返回查询后的表格进行导出
        easyPoiService.downExcel(resp);
    }

    ///所有缺陷表格导入
    @PostMapping("/upload")
    @ResponseBody
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        //导入文件进行处理，拿到返回导入结果
        Result result= easyPoiService.uploadExcel(file);
        System.out.println("导入信息为："+result);
        return result;
    }

    //所有员工数据导出
    @GetMapping("/empDownload")
    @ResponseBody
    public void empDownload(HttpServletResponse resp) throws IOException {
        //根据条件返回查询后的表格进行导出
        easyPoiService.empDownload(resp);
    }

    ///人员excel表格导入
    @PostMapping("/empUpload")
    @ResponseBody
    public Result empUpload(@RequestParam("file") MultipartFile file) throws Exception {
        //导入文件进行处理，拿到返回导入结果
        return easyPoiService.empUpload(file);
    }

    //所有地区数据导出
    @GetMapping("/areaDownload")
    @ResponseBody
    public void areaDownload(HttpServletResponse resp) throws IOException {
        //根据条件返回查询后的表格进行导出
        easyPoiService.areaDownload(resp);
    }

    ///人员excel表格导入
    @PostMapping("/areaUpload")
    @ResponseBody
    public Result areaUpload(@RequestParam("file") MultipartFile file) throws Exception {
        //导入文件进行处理，拿到返回导入结果
        return easyPoiService.areaUpload(file);
    }
}
