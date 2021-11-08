package com.liuwohe.service;

import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EasyPoiService {
    //查询所有缺陷数据，返回查询后结果生成表格并导出
    void downExcel(HttpServletResponse resp) throws IOException;

    //传入缺陷表格文件,执行导入方法，返回导入结果
    Result uploadExcel(MultipartFile file) throws Exception;

    //导出人员数据存入EXCEl
    void empDownload(HttpServletResponse resp) throws IOException;

    //人员excel表格导入
    List<EmpEntity> empUpload(MultipartFile file, HttpServletResponse resp) throws Exception;
}
