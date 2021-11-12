package com.liuwohe.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.liuwohe.entity.AreasEntity;
import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.repository.AreasEntityMapper;
import com.liuwohe.repository.DefectEntityMapper;
import com.liuwohe.repository.EmpEntityMapper;
import com.liuwohe.service.EasyPoiService;
import com.liuwohe.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.liuwohe.service.impl.DefectServiceImpl.filePath;

@Service
public class EasyPoiServiceImpl implements EasyPoiService {

    @Autowired
    DefectEntityMapper defMapper;
    @Autowired
    EmpEntityMapper empMapper;
    @Autowired
    AreasEntityMapper areasMapper;
    //引入实体类对象便于调用AR方法
    DefectEntity def = new DefectEntity();
    EmpEntity emp=new EmpEntity();
    AreasEntity area=new AreasEntity();
    Result res= new Result();

    /**
     * 缺陷表导入导出
     * */
    @Override
    //导出缺陷数据生成表格
    public void downExcel(HttpServletResponse resp) throws IOException {
        List<DefectEntity> defList = def.selectAll();
        defList.forEach(d->{
            if(d.getImage()!=null){
                //设置图片路径
                d.setImage(filePath+"\\"+d.getImage());
            }
        });
        //调用Excel工具类的方法，传入结果列表，响应到前端页面
        ExcelUtils.exportExcel(defList, "缺陷信息统计", "缺陷信息", DefectEntity.class, "缺陷信息统计表", resp);
    }

    @Override
    //导入缺陷数据数据校验后存入数据库并返回错误信息
    public Result uploadExcel(MultipartFile file) throws Exception {
            //调用工具中的方法，导入表格数据并解析
            ExcelImportResult<DefectEntity> result = easPoiUtil(file, DefectEntity.class);
            //导入成功的数据,返回生成的list
            List<DefectEntity> list = result.getList();
            //循环导入每一条数据
            list.forEach(l->{
                if(l.getImage()!=null){
                    String filename=l.getImage().substring(l.getImage().lastIndexOf("\\")+1);
                    l.setImage(filename);
                }
                defMapper.insert(l);
            });
            //失败结果集
            List<DefectEntity> failList = result.getFailList();
            System.out.println("/失败结果集"+failList);
            //验证是否有失败的数据
            if (result.isVerfiyFail()) {
                //错误行
                int rowNum=failList.get(0).getRowNum()+1;
                //失败原因
                String errorMsg = failList.get(0).getErrorMsg();
                res.setCode("400");
                //将失败原因封装并返回
                res.setMsg("导入成功数据:"+list.size()+"条;"+"导入失败数据所在行数为:"+rowNum+";"+"导入失败原因:"+errorMsg);
                return res;
            }
            //返回结果信息
            res.setMsg("导入成功");
            res.setCode("200");
            return res;
    }


    /**
     * 人员表导入导出
     * */
    //导出人员数据存入EXCEl
    @Override
    public void empDownload(HttpServletResponse resp) throws IOException {
        List<EmpEntity> empList = emp.selectAll();
        ExcelUtils.exportExcel(empList,"人员数据表","人员数据",EmpEntity.class,"人员数据表",resp);
    }

    //人员excel表格导入
    @Override
    public Result empUpload(MultipartFile file) throws Exception {
        //调用工具中的方法，导入表格数据并解析
        ExcelImportResult<EmpEntity> result = easPoiUtil(file, EmpEntity.class);
        //导入成功的数据,返回生成的list
        List<EmpEntity> list = result.getList();
        //循环导入每一条数据
        list.forEach(l->{
                empMapper.insert(l);
        });
        //失败结果集
        List<EmpEntity> failList = result.getFailList();
        //验证是否有失败的数据
        if (result.isVerfiyFail()) {
            //错误行
            int rowNum=failList.get(0).getRowNum()+1;
            //失败原因
            String errorMsg = failList.get(0).getErrorMsg();
            res.setCode("400");
            //将失败原因封装并返回
            res.setMsg("导入成功数据:"+list.size()+"条;"+"导入失败数据所在行数为:"+rowNum+";"+"导入失败原因:"+errorMsg);
            return res;
        }
        //返回结果信息
        res.setMsg("导入成功");
        res.setCode("200");
        return res;
        }


    /**
     * 区域表导入导出
     * */
    //区域表数据导出到excel并返回页面
    @Override
    public void areaDownload(HttpServletResponse resp) throws IOException {
        List<AreasEntity> areasList = area.selectAll();
        ExcelUtils.exportExcel(areasList,"区域数据表","区域数据",AreasEntity.class,"区域表",resp);
    }

    //区域表数据导入后读取数据校验后存入数据库
    @Override
    public Result areaUpload(MultipartFile file) throws Exception {
        ExcelImportResult<AreasEntity> result = easPoiUtil(file, AreasEntity.class);
        //导入成功的数据,返回生成的list
        List<AreasEntity> list = result.getList();
        //循环导入每一条数据
        list.forEach(l->{
            areasMapper.insert(l);
        });
        //失败结果集
        List<AreasEntity> failList = result.getFailList();
        //验证是否有失败的数据
        if (result.isVerfiyFail()) {
            //错误行
            int rowNum=failList.get(0).getRowNum()+1;
            //失败原因
            String errorMsg = failList.get(0).getErrorMsg();
            res.setCode("400");
            //将失败原因封装并返回
            res.setMsg("导入成功数据:"+list.size()+"条;"+"导入失败数据所在行数为:"+rowNum+";"+"导入失败原因:"+errorMsg);
            return res;
        }
        //返回结果信息
        res.setMsg("导入成功");
        res.setCode("200");
        return res;
    }


    /**
     * 表格导入工具方法
     * 导入完成后返回读取到的数据
     * */
    public <T> ExcelImportResult<T> easPoiUtil(MultipartFile file, Class<?> pojoClass) throws Exception {
        //导入的基本配置
        ImportParams params = new ImportParams();
        //表头一行
        params.setHeadRows(1);
        //标题一行
        params.setTitleRows(1);
        //代表导入这里是需要验证的（根据字段上的注解校验）
        params.setNeedVerify(true);
        //调用工具中的方法，导入表格数据并解析
        return ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
    }

}
