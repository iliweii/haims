package com.haims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.haims.constant.Constant;
import com.haims.pojo.Type;
import com.haims.pojo.TypeExample;
import com.haims.service.TypeService;
import com.haims.util.UUIDUtil;

@Controller
@RequestMapping("/type")
public class TypeController {

	@Autowired
	TypeService typeService;

	/**
	 * 增加分类信息
	 * 
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/typeadd", produces = "text/html;charset=UTF-8")
	public String typeadd(Type type) {
		type.setId(UUIDUtil.getUUID());
		type.setState(Constant.TYPE_STATE_USE);
		if (typeService.insert(type) > 0) {
			return "1";
		}
		return "增加失败";
	}

	/**
	 * 分类列表
	 * 
	 * @return
	 */
	@RequestMapping(value="/typelist", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String typelist() {
		TypeExample typeExample = new TypeExample();
		// 设置查询条件，分类未被删除的
		typeExample.createCriteria().andStateEqualTo(Constant.TYPE_STATE_USE);
		return JSON.toJSONString(typeService.selectByExample(typeExample));
	}

	/**
	 * 进入修改分类信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("type_update");
		mv.addObject("type", typeService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改分类信息
	 * 
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/typeupdate", produces = "text/html;charset=UTF-8")
	public String typeupdate(Type type) {
		TypeExample updateExample = new TypeExample();
		if (typeService.updateByPrimaryKeySelective(type) > 0) {
			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除分类信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		Type type = typeService.selectByPrimaryKey(id);
		type.setState(Constant.TYPE_STATE_UNUSE);
		if (typeService.updateByPrimaryKeySelective(type) > 0) {
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
	public String deteteByIds(String[] ids) {
		if (ids.length > 0) {
			Type type = null;
			for (String id : ids) {
				type = typeService.selectByPrimaryKey(id);
				type.setState(Constant.TYPE_STATE_UNUSE);
				typeService.updateByPrimaryKeySelective(type);
			}
			return "删除成功";
		}
		return "删除失败";
	}

}
