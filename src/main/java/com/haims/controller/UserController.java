package com.haims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.haims.constant.Constant;
import com.haims.pojo.User;
import com.haims.pojo.UserExample;
import com.haims.service.UserService;
import com.haims.util.UUIDUtil;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * 登录
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(User user, HttpServletRequest request) {
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		HttpSession session = request.getSession();
		// 设置查找条件
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone())
				.andPasswordEqualTo(user.getPassword())
				.andStateEqualTo(Constant.USER_STATE_USE);
		if (userService.selectByExample(userExample).size() > 0) {
			session.setAttribute(Constant.SESSION_USER, userService
					.selectByExample(userExample).get(0).getId());
			session.setAttribute(
					Constant.SESSION_USER_ROLE,
					String.valueOf(userService.selectByExample(userExample)
							.get(0).getRoleid()));
			return "1";
		}
		return "账号或是密码错误!";
	}

	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loginout")
	public String loginout(HttpSession session) {
		session.removeAttribute(Constant.SESSION_USER);
		session.removeAttribute(Constant.SESSION_USER_ROLE);
		return "login";
	}

	/**
	 * 增加用户信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/useradd", produces = "text/html;charset=UTF-8")
	public String useradd(User user) {
		user.setId(UUIDUtil.getUUID());
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone())
				.andStateEqualTo(Constant.USER_STATE_USE);
		user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
		if (userService.selectByExample(userExample).size() > 0) {
			return "手机号已被占用";
		}
		user.setState(Constant.USER_STATE_USE);
		if (userService.insert(user) > 0) {
			return "1";
		}
		return "增加失败";
	}

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/userlist")
	@ResponseBody
	public String userlist() {
		UserExample phoneExample = new UserExample();
		// 不能被查到删除的角色信息以及系统管理员信息
		phoneExample.createCriteria().andIdNotEqualTo(Constant.SYS_ADMIN_ID)
				.andStateEqualTo(Constant.USER_STATE_USE);
		List<User> users = userService.selectByExample(phoneExample);
		for (int i = 0; i < users.size(); i++) {
			users.get(i).setPassword("(******)MD5已加密");
		}
		return JSON.toJSONString(users);
	}

	/**
	 * 进入修改用户信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("user_update");
		mv.addObject("user", userService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userupdate", produces = "text/html;charset=UTF-8")
	public String userupdate(User user) {
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		// 查询原用户信息
		UserExample updateExample = new UserExample();
		updateExample.createCriteria().andIdEqualTo(user.getId());
		User user2 = userService.selectByPrimaryKey(user.getId());
		if (user2 != null) {
			// 如果手机号更改了,先对手机号进行查重操作，然后更改。
			if (user2.getPhone() != user.getPhone()
					&& !user2.getPhone().equals(user.getPhone())) {
				// 手机号查重
				UserExample phoneExample = new UserExample();
				phoneExample.createCriteria().andPhoneEqualTo(user.getPhone());
				if (userService.selectByExample(phoneExample).size() > 0) {
					return "手机号已被占用";
				} else {
					if (userService.updateByExampleSelective(user,
							updateExample) > 0) {
						return "1";
					}
				}
			}
			if (userService.updateByExampleSelective(user, updateExample) > 0) {
				return "1";
			}
		}
		return "修改失败";
	}

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		User user = userService.selectByPrimaryKey(id);
		user.setState(Constant.USER_STATE_UNUSE);
		if (userService.updateByPrimaryKeySelective(user) > 0) {
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
			User user = null;
			for (String id : ids) {
				user = userService.selectByPrimaryKey(id);
				user.setState(Constant.USER_STATE_UNUSE);
				userService.updateByPrimaryKeySelective(user);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 进入修改用户信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findByIdToPassword")
	public ModelAndView findByIdToPassword(String id) {
		ModelAndView mv = new ModelAndView("user_password");
		mv.addObject("user", userService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String resetPassword(User user) {
		user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
		if (userService.updateByPrimaryKeySelective(user) > 0) {
			return "1";
		}
		return "修改失败";
	}

	@ResponseBody
	@RequestMapping(value = "/userInfo", produces = "text/html;charset=UTF-8")
	public String userInfo(HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		User user = userService.selectByPrimaryKey(userid);
		return JSON.toJSON(user).toString();
	}

	// 修改用户信息

	@RequestMapping("/findByPhone")
	@ResponseBody
	public String findByPhone(User user) {
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone());
		if (userService.selectByExample(userExample).size() > 0) {
			return "1";
		}
		return "0";
	}

	/**
	 * 按照角色查询
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/findByroleId", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findByroleId(int roleId) {
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andRoleidEqualTo(roleId);
		return JSON.toJSONString(userService.selectByExample(userExample));
	}

}
