package com.haims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.haims.constant.Constant;
import com.haims.pojo.Supplier;
import com.haims.pojo.SupplierExample;
import com.haims.pojo.SupplierType;
import com.haims.pojo.SupplierTypeExample;
import com.haims.service.SupplierService;
import com.haims.service.SupplierTypeService;
import com.haims.service.TypeService;
import com.haims.util.StringUtil;
import com.haims.util.UUIDUtil;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	SupplierService supplierService;
	@Autowired
	SupplierTypeService supplierTypeService;
	@Autowired
	TypeService typeService;

	/**
	 * 增加分类信息，增加供货商信息是，顺便增加供货商类别信息
	 * 
	 * @param supplier
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/supplieradd", produces = "text/html;charset=UTF-8")
	public String supplieradd(Supplier supplier, String typeId) {
		String supplierId = UUIDUtil.getUUID();
		supplier.setId(supplierId);
		supplier.setState(Constant.TYPE_STATE_USE);

		SupplierType supplierType = null;

		if (supplierService.insert(supplier) > 0) {
			if (!StringUtil.isEmpty(typeId)) {
				for (String i : typeId.split(",")) {
					supplierType = new SupplierType();
					supplierType.setId(UUIDUtil.getUUID());
					supplierType.setSupplierId(supplierId);
					supplierType.setTypeId(i);
					supplierTypeService.insertSelective(supplierType);
				}
			}
			return "1";
		}
		return "增加失败";
	}

	/**
	 * 供货商列表
	 * 
	 * @return
	 */
	@RequestMapping(value="/supplierlist", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String supplierlist() {
		SupplierExample supplierExample = new SupplierExample();
		// 设置查询条件，供货商未被删除的
		supplierExample.createCriteria().andStateEqualTo(
				Constant.TYPE_STATE_USE);
		return JSON.toJSONString(supplierService
				.selectByExample(supplierExample));
	}

	/**
	 * 进入修改供货商信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("supplier_update");
		mv.addObject("supplier", supplierService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改供货商信息,修改供货商信息时注意供货商信息是否变更
	 * 
	 * @param supplier
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/supplierupdate", produces = "text/html;charset=UTF-8")
	public String supplierupdate(Supplier supplier, String typeId) {
		SupplierTypeExample supplierTypeExample = new SupplierTypeExample();
		supplierTypeExample.createCriteria().andSupplierIdEqualTo(
				supplier.getId());
		supplierService.updateByPrimaryKey(supplier);
		// 删除原来的供应商供货商
		supplierTypeService.deleteByExample(supplierTypeExample);
		if (!StringUtil.isEmpty(typeId)) {

			SupplierType supplierType = null;
			// 重新添加
			for (String typeid : typeId.split(",")) {
				supplierType = new SupplierType();
				supplierType.setId(UUIDUtil.getUUID());
				supplierType.setSupplierId(supplier.getId());
				supplierType.setTypeId(typeid);
				supplierTypeService.insertSelective(supplierType);
			}
			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除供货商信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		Supplier supplier = supplierService.selectByPrimaryKey(id);
		supplier.setState(Constant.TYPE_STATE_UNUSE);
		if (supplierService.updateByPrimaryKeySelective(supplier) > 0) {
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 批量删除供货商信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids) {
		if (ids.length > 0) {
			Supplier supplier = null;
			for (String id : ids) {
				supplier = supplierService.selectByPrimaryKey(id);
				supplier.setState(Constant.TYPE_STATE_UNUSE);
				supplierService.updateByPrimaryKeySelective(supplier);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 通过类别查询供货商信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findByType", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findByType(String typeId) {
		// 构建查询条件，通过购货商类别查询
		SupplierTypeExample supplierTypeExample = new SupplierTypeExample();
		supplierTypeExample.createCriteria().andTypeIdEqualTo(typeId);
		List<SupplierType> supplierTypes = supplierTypeService
				.selectByExample(supplierTypeExample);
		for (SupplierType supplierType : supplierTypes) {
			supplierType.setSupplierName(supplierService.selectByPrimaryKey(
					supplierType.getSupplierId()).getName());
		}
		return JSON.toJSONString(supplierTypes);
	}

}
