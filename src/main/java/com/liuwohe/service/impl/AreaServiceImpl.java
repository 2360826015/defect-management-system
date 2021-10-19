package com.liuwohe.service.impl;

import com.liuwohe.entity.AreasEntity;
import com.liuwohe.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {


    @Override
    public List<AreasEntity> getAreaList() {
        AreasEntity ar = new AreasEntity();
        return ar.selectAll();
    }
}
