package com.liuwohe.controller;

import com.liuwohe.entity.EmpEntity;
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

    //表格导出
    @GetMapping("/download")
    @ResponseBody
    public void downExcel(HttpServletResponse resp) throws IOException {
        //根据条件返回查询后的表格进行导出
        easyPoiService.downExcel(resp);
    }

    //表格导入
    @PostMapping("/upload")
    @ResponseBody
    public List<EmpEntity> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletResponse resp) throws Exception {
        //导入文件进行处理，拿到返回导入结果
        List<EmpEntity> failList = easyPoiService.uploadExcel(file,resp);
        return failList;
    }
}
