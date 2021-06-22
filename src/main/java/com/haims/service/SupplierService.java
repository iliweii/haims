package com.haims.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haims.pojo.Supplier;
import com.haims.pojo.SupplierExample;

public interface SupplierService {
	long countByExample(SupplierExample example);

	int deleteByExample(SupplierExample example);

	int deleteByPrimaryKey(String id);

	int insert(Supplier record);

	int insertSelective(Supplier record);

	List<Supplier> selectByExample(SupplierExample example);

	Supplier selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Supplier record,
			@Param("example") SupplierExample example);

	int updateByExample(@Param("record") Supplier record,
			@Param("example") SupplierExample example);

	int updateByPrimaryKeySelective(Supplier record);

	int updateByPrimaryKey(Supplier record);
}
