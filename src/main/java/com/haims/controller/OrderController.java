package com.haims.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haims.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.haims.constant.Constant;
import com.haims.pojo.Order;
import com.haims.pojo.OrderExample;
import com.haims.pojo.Stuff;
import com.haims.service.OrderService;
import com.haims.service.StuffService;
import com.haims.util.UUIDUtil;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private StuffService stuffService;

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 增加订单信息 订单增加时根据订单的类别进行库存判断
	 * 
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orderadd", produces = "text/html;charset=UTF-8")
	public String orderadd(Order order, HttpSession session) {
		order.setId(UUIDUtil.getUUID());
		order.setUserId((String) session.getAttribute(Constant.SESSION_USER));
		order.setTime(formatter.format(new Date()));
		order.setIsDelete(Constant.ORDER_DELETE_0);
		Stuff stuff = stuffService.selectByPrimaryKey(order.getStuffId());
		/* 购入和卖出、退货订单区分 */
		if (order.getState() == 1) {// 购入订单
			// 对家电库存表中的库存进行新增
			stuff.setNumber(String.valueOf(Integer.parseInt(order.getNumber())
					+ Integer.parseInt(stuff.getNumber())));
		} else {// 卖出、退货订单
			// 家电库存表中库存减
			stuff.setNumber(String.valueOf(Integer.parseInt(stuff.getNumber())
					- Integer.parseInt(order.getNumber())));
		}
		// 更新库存信息
		stuffService.updateByPrimaryKeySelective(stuff);
		if (orderService.insert(order) > 0) {
			return "1";
		}
		return "增加失败";
	}

	/**
	 * 管理员订单列表
	 * 
	 * @return
	 */
	@RequestMapping("/orderlistadmin")
	@ResponseBody
	public String orderlistadmin() {
		OrderExample orderExample = new OrderExample();
		// 设置查询条件，查询订单未被删除的
		orderExample.createCriteria().andIsDeleteNotEqualTo(
				Constant.ORDER_DELETE_2);
		return JSON.toJSONString(orderService.selectByExample(orderExample));
	}

	/**
	 * 订单管理员订单列表
	 * 
	 * @return
	 */
	@RequestMapping(value="/orderlist", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String orderlist() {
		OrderExample orderExample = new OrderExample();
		// 设置查询条件，查询订单未被删除的
		orderExample.createCriteria().andIsDeleteEqualTo(
				Constant.ORDER_DELETE_0);
		return JSON.toJSONString(orderService.selectByExample(orderExample));
	}

	/**
	 * 进入修改订单信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findByIdview")
	public ModelAndView findByIdview(String id) {
		ModelAndView mv = new ModelAndView("order_detail");
		mv.addObject("order", orderService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 进入修改订单信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("order_update");
		mv.addObject("order", orderService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改订单信息
	 * 
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orderupdate", produces = "text/html;charset=UTF-8")
	public String orderupdate(Order order) {
		Stuff stuff = stuffService.selectByPrimaryKey(order.getStuffId());
		/* 购入和卖出、退货订单区分 */
		Order order_old = orderService.selectByPrimaryKey(order.getId());
		// 当前库存数量
		int number = Integer.parseInt(stuff.getNumber());
		if (order_old.getState() == 1) {// 购入订单
			// 当前库存数量减去上次订单数量
			number -= Integer.parseInt(order_old.getNumber());
		} else {// 卖出、退货订单
			// 当前库存数量加上上次订单数量
			number += Integer.parseInt(order_old.getNumber());
		}
		if (order.getState() == 1) {// 购入订单
			// 对家电库存表中的库存进行增加
			number += Integer.parseInt(order.getNumber());
		} else {// 卖出、退货订单
			// 家电库存表中库存减
			number -= Integer.parseInt(order.getNumber());
		}
		stuff.setNumber(String.valueOf(number));
		// 更新库存信息
		stuffService.updateByPrimaryKeySelective(stuff);
		if (orderService.updateByPrimaryKeySelective(order) > 0) {
			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除订单信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(int flag, String id) {
		// flag 0 订单管理员
		Order order = orderService.selectByPrimaryKey(id);
		order.setIsDelete(flag);
		if (orderService.updateByPrimaryKeySelective(order) > 0) {
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 批量删除用户信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(int flag, String[] ids) {
		if (ids.length > 0) {
			Order order = null;
			for (String id : ids) {
				order = orderService.selectByPrimaryKey(id);
				order.setIsDelete(flag);
				orderService.updateByPrimaryKeySelective(order);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public void ExportBankCkeckInfo(HttpServletResponse response, HttpServletRequest request){
		OrderExample orderExample = new OrderExample();
		// 设置查询条件，查询订单未被删除的
		orderExample.createCriteria().andIsDeleteNotEqualTo(
				Constant.ORDER_DELETE_2);
		//得到所有要导出的数据
		List<Order> orderlist = orderService.selectByExample(orderExample);
		for (int i = 0; i < orderlist.size(); i++) {
			if (orderlist.get(i).getState() == 1) {
				orderlist.get(i).setUserId("入库");
			} else if (orderlist.get(i).getState() == 2) {
				orderlist.get(i).setUserId("销售");
			} else if (orderlist.get(i).getState() == 3) {
				orderlist.get(i).setUserId("退货");
			}
		}
		//定义导出的excel名字
		String excelName = "家电库存管理系统订单详情表" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();

		//获取需要转出的excel表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		fieldMap.put("id","订单编号");
		fieldMap.put("name","订单名称");
		fieldMap.put("money","交易价格");
		fieldMap.put("number","交易数量");
		fieldMap.put("userId","订单类型");
		fieldMap.put("typeId","家电类型编号");
		fieldMap.put("typeName","家电类型名称");
		fieldMap.put("stuffId","家电编号");
		fieldMap.put("stuffName","家电名称");
		fieldMap.put("supplierId","家电供货商编号");
		fieldMap.put("supplierName","家电供货商名称");
		fieldMap.put("time","交易时间");
		fieldMap.put("userName","订单操作员");

		//导出用户相关信息
		new ExportExcelUtil().export(excelName,orderlist,fieldMap,response);
	}

}
