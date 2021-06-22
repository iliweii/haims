package com.haims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haims.dao.RoleMapper;
import com.haims.dao.UserMapper;
import com.haims.pojo.User;
import com.haims.pojo.UserExample;
import com.haims.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	RoleMapper roleMapper;

	@Override
	public long countByExample(UserExample example) {
		// TODO Auto-generated method stub
		return userMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(UserExample example) {
		// TODO Auto-generated method stub
		return userMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		// TODO Auto-generated method stub
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(record);
	}

	@Override
	public List<User> selectByExample(UserExample example) {
		// TODO Auto-generated method stub
		List<User> users = userMapper.selectByExample(example);
		for (User user : users) {
			user.setRoleName(roleMapper.selectByPrimaryKey(user.getRoleid())
					.getName());
		}
		return users;
	}

	@Override
	public User selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		User user = userMapper.selectByPrimaryKey(id);
		user.setRoleName(roleMapper.selectByPrimaryKey(user.getRoleid())
				.getName());
		return user;
	}

	@Override
	public int updateByExampleSelective(User record, UserExample example) {
		// TODO Auto-generated method stub
		return userMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(User record, UserExample example) {
		// TODO Auto-generated method stub
		return userMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(record);
	}

}
