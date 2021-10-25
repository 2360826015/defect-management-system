package com.liuwohe.service;

import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DefectService {
//    传入审核人员id验证通过后返回缺陷列表展示
    Result getDefectList(String id);

    //添加一条缺陷记录
    //缺陷编辑时，传入该数据中的缺陷编号id
    Result addorEditDefect(DefectEntity def, MultipartFile file) throws IOException;

    //根据缺陷表单的defect_id删除当前数据
    Result delDefect(DefectEntity def);
    //传入要发送的缺陷列表
    Result sendDefect(DefectEntity def);
    //    跳转到缺陷修改页面,根据缺陷报告id返回当前缺陷数据
    Result getDefectById(String id);
    //评定缺陷紧急程度，审核需要修复的缺陷
    Result censorDefect(DefectEntity def);

}
