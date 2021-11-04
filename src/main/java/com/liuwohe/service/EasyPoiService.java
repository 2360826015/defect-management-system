package com.liuwohe.service;

import com.liuwohe.entity.EmpEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EasyPoiService {
    //传入条件查询条件，返回查询后结果生成表格并导出
    void downExcel(HttpServletResponse resp) throws IOException;

    //传入文件,执行导入方法，返回导入结果
    List<EmpEntity> uploadExcel(MultipartFile file, HttpServletResponse resp) throws Exception;
}
