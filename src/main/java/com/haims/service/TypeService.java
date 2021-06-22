package com.haims.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haims.pojo.Type;
import com.haims.pojo.TypeExample;

public interface TypeService {
	long countByExample(TypeExample example);

	int deleteByExample(TypeExample example);

	int deleteByPrimaryKey(String id);

	int insert(Type record);

	int insertSelective(Type record);

	List<Type> selectByExample(TypeExample example);

	Type selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Type record,
			@Param("example") TypeExample example);

	int updateByExample(@Param("record") Type record,
			@Param("example") TypeExample example);

	int updateByPrimaryKeySelective(Type record);

	int updateByPrimaryKey(Type record);
}
