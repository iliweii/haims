package com.haims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haims.dao.OrderMapper;
import com.haims.dao.StuffMapper;
import com.haims.dao.SupplierMapper;
import com.haims.dao.TypeMapper;
import com.haims.dao.UserMapper;
import com.haims.pojo.Order;
import com.haims.pojo.OrderExample;
import com.haims.pojo.Stuff;
import com.haims.pojo.Supplier;
import com.haims.pojo.Type;
import com.haims.pojo.User;
import com.haims.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private TypeMapper typeMapper;
	@Autowired
	private StuffMapper stuffMapper;
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public long countByExample(OrderExample example) {
		// TODO Auto-generated method stub
		return orderMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(OrderExample example) {
		// TODO Auto-generated method stub
		return orderMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Order record) {
		// TODO Auto-generated method stub
		return orderMapper.insert(record);
	}

	@Override
	public int insertSelective(Order record) {
		// TODO Auto-generated method stub
		return orderMapper.insertSelective(record);
	}

	@Override
	public List<Order> selectByExample(OrderExample example) {
		// 查询所有订单信息
		List<Order> orders = orderMapper.selectByExample(example);
		Type type = null;
		Stuff stuff = null;
		Supplier supplier = null;
		User user = null;
		for (Order order : orders) {
			// 设置订单中的类别信息
			type = typeMapper.selectByPrimaryKey(order.getTypeId());
			order.setType(type);
			order.setTypeName(type.getType());

			// 设置订单中的供货商信息
			supplier = supplierMapper.selectByPrimaryKey(order.getSupplierId());
			order.setSupplier(supplier);
			order.setSupplierName(supplier.getName());

			// 设置订单中的商品信息
			stuff = stuffMapper.selectByPrimaryKey(order.getStuffId());
			order.setStuff(stuff);
			order.setStuffName(stuff.getName());

			// 设置订单中的人员细信息
			user = userMapper.selectByPrimaryKey(order.getUserId());
			order.setUser(user);
			order.setUserName(user.getName());
		}
		// TODO Auto-generated method stub
		return orders;
	}

	@Override
	public Order selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(Order record, OrderExample example) {
		// TODO Auto-generated method stub
		return orderMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Order record, OrderExample example) {
		// TODO Auto-generated method stub
		return orderMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		// TODO Auto-generated method stub
		return orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Order record) {
		// TODO Auto-generated method stub
		return orderMapper.updateByPrimaryKey(record);
	}

}
