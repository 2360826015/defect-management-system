package com.liuwohe.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.repository.AreasEntityMapper;
import com.liuwohe.repository.DefectEntityMapper;
import com.liuwohe.repository.EmpEntityMapper;
import com.liuwohe.service.DefectService;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@SuppressWarnings("ALL")
public class DefectServiceImpl implements DefectService {

    @Autowired
    EmpEntityMapper empMapper;
    @Autowired
    AreasEntityMapper areasMapper;
    @Autowired
    EmpService empService;
    @Autowired
    DefectEntityMapper defectMapper;

    //创建mybatis-plus查询包装器
//    QueryWrapper<Object> qw = new QueryWrapper<>();
    //引入实体类方法
    DefectEntity defect = new DefectEntity();
    //使用工具实体类存放返回信息
    Result result = new Result();
    //保存图片的默认路径
    static String filePath = "D:\\JavaProject\\defect-management-system\\src\\main\\resources\\static\\assets\\images";

    //[巡检/审核人员]传入使用者id验证是否为审核人员或者巡检人员返回缺陷列表数据
    @Override
    public Result getDefectList(String id) {
        Result result = new Result();
//        根据id查询数据库
        EmpEntity emp = empMapper.selectById(id);
        System.out.println("查询后传入用户数据"+emp);
        //如果用户角色为审核人员,则返回对应数据,否则返回失败提示
        QueryWrapper<Object> qw = new QueryWrapper<>();
        if("censor".equals(emp.getUserRole())){

            result.setCode("200");
            result.setMsg("获取待审核缺陷记录成功");
//            插入查询条件,状态为待审核时,审核人员可见
            qw.eq("status","待审核")
                    .or()
                    .eq("status","已审核");
            List<DefectEntity> defList = defect.selectList(qw);
            //遍历存入员工数据、地区数据
            defList.forEach(d->{
                d.setEmp(empService.getUserById(d.getUserId()));
            });
            result.setData(defList);
            return result;
        }else if("inspection".equals(emp.getUserRole())){
            result.setCode("200");
            result.setMsg("获取已保存记录成功");
//            QueryWrapper<Object> qw = new QueryWrapper<>();
//           插入查询条件,状态为已保存时,巡检人员可见
            qw.eq("user_id",emp.getUserId())
                    .and(q->q.eq("status","已保存")
                            .or()
                            .eq("status","已驳回")
                            .or()
                            .eq("status","待复检")
                        );
            List<DefectEntity> defList = defect.selectList(qw);
            //遍历存入员工数据、地区数据
            defList.forEach(d->{
                d.setEmp(empService.getUserById(d.getUserId()));
            });
            result.setData(defList);
            System.out.println(defList);
            return result;
        }
        result.setCode("400");
        result.setMsg("获取缺陷表失败,请检查账号是否为审核账号");
        return result;
    }

    //[巡检/审核人员]添加并保存一条缺陷记录
    @Override
    public Result addorEditDefect(DefectEntity def, MultipartFile defImage) throws IOException {
//        判断是否上传图片
        if(!defImage.isEmpty()){

            //获取原始图片的拓展名
            String originalFilename = defImage.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
//        从.开始截取获得后缀名
            String suffix= originalFilename.substring(i);
            //新的文件名字
            String newFileName = UUID.randomUUID() + suffix;
            //封装上传文件位置的全路径
            File targetFile = new File(filePath, newFileName);

            if(!".png".equals(suffix)&&!".jpg".equals(suffix)){
                result.setMsg("图片格式错误，请上传jpg或者png格式图片");
                result.setCode("400");
                return result;
            }

            //把本地文件上传到封装上传文件位置的全路径
            defImage.transferTo(targetFile);
//            设置更新后的文件名
            def.setImage(newFileName);
        }

        //对传入的数据进行校验
        if(def.getAreaId() == null||def.getAreaId().trim().length()<=0){
            result.setMsg("所巡检的区域不能空");
            result.setCode("400");
            return result;
        }else if(def.getLocation()==null||def.getLocation().trim().length()<=0){
            result.setMsg("请填报当前区域是否完成巡检");
            result.setCode("400");
            return result;
        } else if(def.getPhone().trim().length()!=11) {
            result.setMsg("请检查电话号码格式");
            result.setCode("400");
            return result;
        }else if(def.getDefectMsg().trim().length()<=0){
            result.setMsg("请描述缺陷问题情况");
            result.setCode("400");
            return result;
        }
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
    public Result delDefect(String id) {
        DefectEntity def = defectMapper.selectById(id);
        File file = new File(filePath +"\\"+ def.getImage());
        System.out.println("file"+file);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + def.getImage() + "成功！");
                return result;
            } else {
                System.out.println("删除单个文件" + def.getImage() + "失败！");
                return result;
            }
        }
        System.out.println("删除单个文件失败：" + def.getImage() + "不存在！");
        defectMapper.deleteById(id);
        result.setCode("200");
        result.setMsg("删除成功！！");
        return result;
    }


    //[巡检人员]传入要发送的缺陷
    @Override
    public Result sendDefect(String id) {
        DefectEntity def = defectMapper.selectById(id);
        def.setStatus("待审核");
        defectMapper.updateById(def);
        result.setCode("200");
        result.setMsg("发送成功");
        return result;
    }


//    跳转到已保存的缺陷修改页面时返回当前缺陷数据
    @Override
    public Result getDefectById(String id) {
        //查询缺陷单数据并返回
        DefectEntity defect= defectMapper.selectById(id);
        //调用empservice的查询方法，根据id查询员工数据。
        EmpEntity emp = empService.getUserById(defect.getUserId());
        defect.setEmp(emp);
        if("待审核".equals(defect.getStatus())){
            result.setCode("200");
            result.setMsg("获取待审核数据成功");
            result.setData(defect);
            return result;
        }
        result.setCode("200");
        result.setMsg("获取已保存数据成功");
        result.setData(defect);
        return result;
    }


    //[审核人员]评定缺陷紧急程度，审核缺陷问题报告
    @Override
    public Result censorDefect(DefectEntity def) {
        //如果传入的紧急程度状态码是0则执行驳回，缺陷报告状态置为”已驳回“，否则通过审核
         if(def.getUrgently() == null){
            def.setStatus("已驳回");
            defectMapper.updateById(def);
            result.setCode("200");
            result.setMsg("驳回成功！");
            return result;
        }
        def.setStatus("已审核");
        defectMapper.updateById(def);
        result.setCode("200");
        result.setMsg("审核通过！");
        return result;
    }

    //[审核人员]更改缺陷状态
    @Override
    public void reinspection(String id) {
        DefectEntity def = defectMapper.selectById(id);
        def.setStatus("待复检");
        defectMapper.updateById(def);
    }

    //传入缺陷记录的id，更新缺陷状态，返回信息
    @Override
    public Result finishDefect(String id) {
        DefectEntity def = defectMapper.selectById(id);
        def.setStatus("已完成");
        int i = defectMapper.updateById(def);
        if(i==0){
            result.setCode("400");
            result.setMsg("操作失败！！");
            return result;
        }
        result.setCode("200");
        result.setMsg("已完成复检！！");
        return result;
    }

    //根据传入的用户id查询统计不同缺陷状态的数据
    @Override
    public Result getStatistics(String id) {
        QueryWrapper<DefectEntity> qw = new QueryWrapper<>();
        EmpEntity emp = empService.getUserById(id);
//        用于存放统计的数据
        if("manger".equals(emp.getUserRole())){
            //获取数据后执行分类统计出需要的数据,存入返回的结果集
            qw.select("status label","count(*) value");
            qw.groupBy("status");
            List<Map<String, Object>> defMaps = defectMapper.selectMaps(qw);
            System.out.println("defMaps"+defMaps);
            result.setData(defMaps);
            result.setCode("200");
            result.setMsg("获取数据成功！！");
            return result;
        }
        result.setCode("400");
        result.setMsg("权限不足！！");
        return result;
    }

    //或缺所有缺陷数据并返回
    @Override
    public Result getAll() {
        List<DefectEntity> defList = defect.selectAll();
        //遍历存入员工数据、地区数据
        defList.forEach(d->{
            d.setEmp(empService.getUserById(d.getUserId()));
        });
        result.setData(defList);
        result.setCode("200");
        result.setMsg("获取数据成功");
        return result;
    }

}
