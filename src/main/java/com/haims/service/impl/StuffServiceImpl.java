package com.haims.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.haims.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haims.dao.StuffMapper;
import com.haims.dao.TypeMapper;
import com.haims.pojo.Stuff;
import com.haims.pojo.StuffExample;
import com.haims.service.StuffService;

@Service
public class StuffServiceImpl implements StuffService {

    @Autowired
    private StuffMapper stuffMapper;
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public long countByExample(StuffExample example) {
        // TODO Auto-generated method stub
        return stuffMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(StuffExample example) {
        // TODO Auto-generated method stub
        return stuffMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        return stuffMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Stuff record) {
        // TODO Auto-generated method stub
        return stuffMapper.insert(record);
    }

    @Override
    public int insertSelective(Stuff record) {
        // TODO Auto-generated method stub
        return stuffMapper.insertSelective(record);
    }

    @Override
    public List<Stuff> selectByExample(StuffExample example) {
        // TODO Auto-generated method stub
        List<Stuff> stuffs = stuffMapper.selectByExample(example);
        for (Stuff stuff : stuffs) {
            stuff.setTypeName(typeMapper.selectByPrimaryKey(stuff.getTypeId())
                    .getType());
        }
        return stuffs;
    }

    @Override
    public Stuff selectByPrimaryKey(String id) {
        Stuff stuff = stuffMapper.selectByPrimaryKey(id);
        Type type = typeMapper.selectByPrimaryKey(stuff.getTypeId());
        stuff.setTypeName(type.getType());
        // TODO Auto-generated method stub
        return stuff;
    }

    @Override
    public int updateByExampleSelective(Stuff record, StuffExample example) {
        // TODO Auto-generated method stub
        return stuffMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Stuff record, StuffExample example) {
        // TODO Auto-generated method stub
        return stuffMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Stuff record) {
        // TODO Auto-generated method stub
        return stuffMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Stuff record) {
        // TODO Auto-generated method stub
        return stuffMapper.updateByPrimaryKey(record);
    }

}
