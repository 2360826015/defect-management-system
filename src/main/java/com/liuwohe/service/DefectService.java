package com.liuwohe.service;

import com.liuwohe.entity.DefectEntity;
import com.liuwohe.entity.Result;

import java.util.List;

public interface DefectService {
//    传入审核人员id验证通过后返回缺陷列表展示
    Result getDefectList(String id);

    //添加一条缺陷记录
    //缺陷编辑时，传入该数据中的缺陷编号id
    Result addorEditDefect(DefectEntity def);

    //根据缺陷表单的defect_id删除当前数据
    Result delDefect(DefectEntity def);
    //传入要发送的缺陷列表
    Result sendDefect(List<DefectEntity> lDefect);
}
