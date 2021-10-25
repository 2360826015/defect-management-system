package com.liuwohe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.repository.DefectEntityMapper;
import com.liuwohe.repository.EmpEntityMapper;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@SuppressWarnings("ALL")
public class DefectServiceImpl implements DefectService {

    @Autowired
    EmpEntityMapper empMapper;
    @Autowired
    EmpService empService;
    @Autowired
    DefectEntityMapper defectMapper;

    //创建mybatis-plus查询包装器
    QueryWrapper<Object> qw = new QueryWrapper<>();
    //引入实体类方法
    DefectEntity defect = new DefectEntity();
    //使用工具实体类存放返回信息
    Result result = new Result();

    //传入使用者id验证是否为审核人员或者巡检人员返回缺陷列表数据
    @Override
    public Result getDefectList(String id) {
        Result result = new Result();
//        根据id查询数据库
        EmpEntity emp = empMapper.selectById(id);
        //如果用户角色为审核人员,则返回对应数据,否则返回失败提示
        if("censor".equals(emp.getUserRole())){
            result.setCode("200");
            result.setMsg("获取缺陷表成功");
//            插入查询条件,状态为待审核时,审核人员可见
            qw.eq("status","待审核")
                    .or()
                    .eq("status","已出单");
            result.setData(defect.selectList(qw));
            return result;
        }else if("inspection".equals(emp.getUserRole())){
            result.setCode("200");
            result.setMsg("获取已保存记录成功");
//            QueryWrapper<Object> qw = new QueryWrapper<>();
//           插入查询条件,状态为已保存时,巡检人员可见
            qw.eq("status","已保存")
                    .or()
                    .eq("status","已驳回");
            result.setData(defect.selectList(qw));
            return result;
        }
        result.setCode("400");
        result.setMsg("获取缺陷表失败,请检查账号是否为审核账号");
        return result;
    }

    //[巡检人员]添加并保存一条缺陷记录
    @Override
    public Result addorEditDefect(DefectEntity def, MultipartFile file) throws IOException {
        //保存图片的路径
        String filePath = "D:\\javaProject\\defect-management-system\\src\\main\\resources\\images";
        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
//        从.开始截取获得后缀名
        String suffix= originalFilename.substring(i);
        //新的文件名字
        String newFileName = UUID.randomUUID() + suffix;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);

        //对传入的数据进行校验
        if(def.getAreaId() == null||def.getAreaId().trim().length()<=0){
            result.setMsg("所巡检的区域不能空");
            result.setCode("400");
            return result;
        }else if(def.getLocation()==null||def.getLocation().trim().length()<=0){
            result.setMsg("请填报当前区域是否完成巡检");
            result.setCode("400");
            return result;
        }
        else if(def.getPhone()==null||def.getPhone().length()!=11) {
            result.setMsg("请检查电话号码格式");
            result.setCode("400");
            return result;
        }else if(!".png".equals(suffix)&&!".jpg".equals(suffix)){
            result.setMsg("图片格式错误，请上传jpg或者png格式图片");
            result.setCode("400");
            return result;
        }
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        def.setImage(newFileName);
        //判断缺陷编号是否为空，为空则进入添加方法，否则进入编辑方法
        if(def.getDefectId()!=null&&def.getDefectId().trim().length()>0){
//            执行数据更新
            defectMapper.updateById(def);
            result.setCode("200");
            result.setMsg("修改成功！！");
            return result;
        }
        //校验通过后添加默认状态并持久化到数据库
        def.setStatus("已保存");
        defectMapper.insert(def);
        result.setCode("200");
        result.setMsg("保存成功！！");
        return result;
    }

    //[巡检人员]传入defect_id删除当前缺陷数据
    @Override
    public Result delDefect(DefectEntity def) {
        defectMapper.deleteById(def.getDefectId());
        result.setCode("200");
        result.setMsg("删除成功！！");
        return result;
    }


    //[巡检人员]传入要发送的缺陷
    @Override
    public Result sendDefect(DefectEntity def) {
        def.setStatus("待审核");
        defectMapper.updateById(def);
        result.setCode("200");
        result.setMsg("发送成功");
        return result;
    }


//    跳转到已保存的缺陷修改页面时返回当前缺陷数据
    @Override
    public Result getDefectById(String id) {
        DefectEntity defect= defectMapper.selectById(id);
        //调用empservice的查询方法，根据id查询员工数据。
        EmpEntity emp = empService.getUserById(defect.getUserId());
        defect.setEmp(emp);
        result.setCode("200");
        result.setMsg("获取数据成功");
        result.setData(defect);
        return result;
    }


    //[审核人员]评定缺陷紧急程度，通过缺陷审核
    @Override
    public Result censorDefect(DefectEntity def) {
        if(def.getUrgently() == null){
            result.setCode("400");
            result.setMsg("请选择紧急程度");
            return result;
            //如果传入的状态码是0则执行驳回，否则通过审核
        }else if("0".equals(def.getStatus())){
            def.setStatus("已驳回");
            defectMapper.updateById(def);
            result.setCode("200");
            result.setMsg("审核不通过！");
            return result;
        }
        def.setStatus("已出单");
        defectMapper.updateById(def);
        result.setCode("200");
        result.setMsg("审核通过！");
        return result;
    }

}
