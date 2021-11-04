package com.liuwohe.service;

import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DefectService {
//    传入审核人员id验证通过后返回缺陷列表展示
    Result getDefectList(String id);

    //添加一条缺陷记录
    //缺陷编辑时，传入该数据中的缺陷编号id
    Result addorEditDefect(DefectEntity def, MultipartFile defImage) throws IOException;

    //根据缺陷表单的defect_id删除当前数据
    Result delDefect(String id);
    //传入要发送的缺陷列表
    Result sendDefect(String id);
    //    跳转到缺陷修改页面,根据缺陷报告id返回当前缺陷数据
    Result getDefectById(String id);
    //评定缺陷紧急程度，审核缺陷问题报告
    Result censorDefect(DefectEntity def);
    //问题修复后发布复检任务
    void reinspection(String id);
    //传入id执行完成缺陷记录方法
    Result finishDefect(String id);
    //        获取统计信息
    Result getStatistics(String id);
    //或缺所有缺陷数据并返回
    Result getAll();
}
