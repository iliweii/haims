package com.haims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
public class IndexController {

	/**
	 * 进入主页面
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "login";
	}

	@RequestMapping("/page/index")
	public String index1(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String roleid = (String)session.getAttribute("roleid");
		String userid = (String)session.getAttribute("userid");
		model.addAttribute("roleid", roleid);
		model.addAttribute("userid", userid);
		return "index";
	}
	@RequestMapping("/page/order_add")
	public String order_add() {
		return "order_add";
	}
	@RequestMapping("/page/order_detail")
	public String order_detail() {
		return "order_detail";
	}
	@RequestMapping("/page/order_list_admin")
	public String order_list_admin() {
		return "order_list_admin";
	}
	@RequestMapping("/page/order_update")
	public String order_update() {
		return "order_update";
	}
	@RequestMapping("/page/stuff_add")
	public String stuff_add() {
		return "stuff_add";
	}
	@RequestMapping("/page/stuff_list")
	public String stuff_list() {
		return "stuff_list";
	}
	@RequestMapping("/page/stuff_update")
	public String stuff_update() {
		return "stuff_update";
	}
	@RequestMapping("/page/supplier_add")
	public String supplier_add() {
		return "supplier_add";
	}
	@RequestMapping("/page/supplier_list")
	public String supplier_list() {
		return "supplier_list";
	}
	@RequestMapping("/page/supplier_update")
	public String supplier_update() {
		return "supplier_update";
	}
	@RequestMapping("/page/type_add")
	public String type_add() {
		return "type_add";
	}
	@RequestMapping("/page/type_list")
	public String type_list() {
		return "type_list";
	}
	@RequestMapping("/page/type_update")
	public String type_update() {
		return "type_update";
	}
	@RequestMapping("/page/user_add")
	public String user_add() {
		return "user_add";
	}
	@RequestMapping("/page/user_info")
	public String user_info() {
		return "user_info";
	}
	@RequestMapping("/page/user_list")
	public String user_list() {
		return "user_list";
	}
	@RequestMapping("/page/user_password")
	public String user_password() {
		return "user_password";
	}
	@RequestMapping("/page/user_update")
	public String user_update() {
		return "user_update";
	}



}
