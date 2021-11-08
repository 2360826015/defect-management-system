package com.liuwohe.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.repository.DefectEntityMapper;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EasyPoiService;
import com.liuwohe.service.EmpService;
import com.liuwohe.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EasyPoiServiceImpl implements EasyPoiService {

    @Autowired
    DefectEntityMapper defMapper;
    //
    DefectEntity def = new DefectEntity();
    EmpEntity emp=new EmpEntity();
    Result res= new Result();

    @Override
    //导出缺陷数据生成表格
    public void downExcel(HttpServletResponse resp) throws IOException {

        List<DefectEntity> defList = def.selectAll();
        //调用Excel工具类的方法，传入结果列表，响应到前端页面
        ExcelUtils.exportExcel(defList, "缺陷信息统计", "缺陷信息", DefectEntity.class, "缺陷信息统计表", resp);
    }

    @Override
    //导出缺陷数据数据校验后存入数据库并返回错误信息
    public Result uploadExcel(MultipartFile file) throws Exception {
            Map<String, Object> map = new HashMap<>();
            //导入的基本配置
            ImportParams params = new ImportParams();
            //表头一行
            params.setHeadRows(1);
            //标题一行
            params.setTitleRows(1);
            //代表导入这里是需要验证的（根据字段上的注解校验）
            params.setNeedVerify(true);
            //调用工具中的方法，导入表格数据并解析
            ExcelImportResult<DefectEntity> result = ExcelImportUtil.importExcelMore(file.getInputStream(), DefectEntity.class, params);
            //导入成功的数据,返回生成的list
            List<DefectEntity> list = result.getList();
            //循环导入每一条数据
            list.forEach(l->{
                defMapper.insert(l);
            });

            //失败结果集
            List<DefectEntity> failList = result.getFailList();
            //拿到导出失败的工作簿
//            Workbook failWorkbook = result.getFailWorkbook();
            //验证是否有失败的数据
            if (result.isVerfiyFail()) {
                //错误行
                int rowNum=failList.get(0).getRowNum()+1;
                //失败原因
                String errorMsg = failList.get(0).getErrorMsg();
                //将失败原因封装并返回
                res.setMsg("导入成功数据:"+list.size()+"条;"+"导入失败数据所在行数为:"+rowNum+";"+"导入失败原因:"+errorMsg);
                return res;
            }
            //返回结果信息
            res.setMsg("导入成功");
            res.setCode("200");
            return res;
    }

    //导出人员数据存入EXCEl
    @Override
    public void empDownload(HttpServletResponse resp) throws IOException {
        List<EmpEntity> empList = emp.selectAll();
        ExcelUtils.exportExcel(empList,"人员数据表","人员数据",EmpEntity.class,"人员数据表",resp);
    }

    //人员excel表格导入
    @Override
    public List<EmpEntity> empUpload(MultipartFile file, HttpServletResponse resp) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //导入的基本配置
        ImportParams params = new ImportParams();
        //表头一行
        params.setHeadRows(1);
        //标题一行
        params.setTitleRows(1);
        //代表导入这里是需要验证的（根据字段上的注解校验）
        params.setNeedVerify(true);
            /* //开启自定义的校验
            params.setVerifyHandler(MyExcelverifiyUtils);*/
        //调用工具中的方法，导入表格数据并解析
        ExcelImportResult<EmpEntity> result = ExcelImportUtil.importExcelMore(file.getInputStream(), EmpEntity.class, params);
        //导入成功的数据,返回生成的list
        List<EmpEntity> list = result.getList();
        //循环导入每一条数据
        list.forEach(l->{
//                empService.addEmp(l);
        });

        //失败结果集
        List<EmpEntity> failList = result.getFailList();
        //拿到导出失败的工作簿
        Workbook failWorkbook = result.getFailWorkbook();
        //验证是否有失败的数据
        if (result.isVerfiyFail()) {
            ServletOutputStream fos = resp.getOutputStream();
            //mime类型
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("content-Type", "application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表","UTF-8") + ".xls");
            //拿到错误的结果写入到输出中
            failWorkbook.write(fos);
            fos.flush();
            fos.close();
        }
        return failList;

    }

}
