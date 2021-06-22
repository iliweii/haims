package com.haims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haims.dao.SupplierTypeMapper;
import com.haims.dao.TypeMapper;
import com.haims.pojo.SupplierType;
import com.haims.pojo.SupplierTypeExample;
import com.haims.service.SupplierTypeService;

@Service
public class SupplierTypeServiceImpl implements SupplierTypeService {

	@Autowired
	private SupplierTypeMapper supplierTypeMapper;

	@Autowired
	private TypeMapper typeMapper;

	@Override
	public long countByExample(SupplierTypeExample example) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SupplierTypeExample example) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SupplierType record) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(SupplierType record) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.insertSelective(record);
	}

	@Override
	public List<SupplierType> selectByExample(SupplierTypeExample example) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.selectByExample(example);
	}

	@Override
	public SupplierType selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(SupplierType record,
			SupplierTypeExample example) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(SupplierType record, SupplierTypeExample example) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(SupplierType record) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SupplierType record) {
		// TODO Auto-generated method stub
		return supplierTypeMapper.updateByPrimaryKey(record);
	}

}
