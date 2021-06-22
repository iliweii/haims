package com.haims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haims.dao.SupplierMapper;
import com.haims.dao.SupplierTypeMapper;
import com.haims.dao.TypeMapper;
import com.haims.pojo.Supplier;
import com.haims.pojo.SupplierExample;
import com.haims.pojo.SupplierType;
import com.haims.pojo.SupplierTypeExample;
import com.haims.pojo.Type;
import com.haims.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private SupplierTypeMapper supplierTypeMapper;
	@Autowired
	private TypeMapper typeMapper;

	@Override
	public long countByExample(SupplierExample example) {
		// TODO Auto-generated method stub
		return supplierMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(SupplierExample example) {
		// TODO Auto-generated method stub
		return supplierMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return supplierMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.insert(record);
	}

	@Override
	public int insertSelective(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.insertSelective(record);
	}

	/**
	 * 查询供货商信息时，把供货商所有类别查出来
	 */
	@Override
	public List<Supplier> selectByExample(SupplierExample example) {
		// TODO Auto-generated method stub
		// 供货商列表
		List<Supplier> suppliers = supplierMapper.selectByExample(example);

		// 供货商的分类查询条件设定
		SupplierTypeExample supplierTypeExample = new SupplierTypeExample();
		for (Supplier supplier : suppliers) {
			List<Type> types = new ArrayList<>();
			supplierTypeExample.clear();
			supplierTypeExample.createCriteria().andSupplierIdEqualTo(
					supplier.getId());
			List<SupplierType> supplierTypes = supplierTypeMapper
					.selectByExample(supplierTypeExample);
			for (SupplierType supplierType : supplierTypes) {
				types.add(typeMapper.selectByPrimaryKey(supplierType
						.getTypeId()));
			}
			supplier.setTypes(types);
		}
		return suppliers;
	}

	/**
	 * 查询供货商信息时，把供货商所有类别查出来
	 */
	@Override
	public Supplier selectByPrimaryKey(String id) {
		Supplier supplier = supplierMapper.selectByPrimaryKey(id);
		// 供货商的分类查询条件设定
		SupplierTypeExample supplierTypeExample = new SupplierTypeExample();
		List<Type> types = new ArrayList<>();
		supplierTypeExample.createCriteria().andSupplierIdEqualTo(
				supplier.getId());
		List<SupplierType> supplierTypes = supplierTypeMapper
				.selectByExample(supplierTypeExample);
		for (SupplierType supplierType : supplierTypes) {
			types.add(typeMapper.selectByPrimaryKey(supplierType.getTypeId()));
		}
		supplier.setTypes(types);
		return supplier;
	}

	@Override
	public int updateByExampleSelective(Supplier record, SupplierExample example) {
		// TODO Auto-generated method stub
		return supplierMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Supplier record, SupplierExample example) {
		// TODO Auto-generated method stub
		return supplierMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.updateByPrimaryKey(record);
	}

}
