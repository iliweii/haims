package com.haims.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.haims.constant.Constant;
import com.haims.pojo.Stuff;
import com.haims.pojo.StuffExample;
import com.haims.service.StuffService;
import com.haims.util.FileUtil;
import com.haims.util.UUIDUtil;

@Controller
@RequestMapping("/stuff")
public class StuffController {

	@Autowired
	private StuffService stuffService;
	@Value("${file.path}")
	private String fileRootPath;

	/**
	 * 增加材料信息
	 * 
	 * @param stuff
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/stuffadd", produces = "text/html;charset=UTF-8")
	public String stuffadd(Stuff stuff, MultipartFile file,
			HttpServletRequest request) throws Exception {
		File newFile = new File(fileRootPath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		// 上传文件路径及文件名
		String fileName = FileUtil.executeUpload(newFile.getAbsolutePath()
				+ "/", file);

		stuff.setId(UUIDUtil.getUUID());
		stuff.setUrl( "/static/image/"
				+ fileName);
		if (stuffService.insert(stuff) > 0) {
			return "1";
		}
		return "增加失败";
	}

	/**
	 * 材料列表
	 * 
	 * @return
	 */
	@RequestMapping(value="/stufflist", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String stufflist() {
		StuffExample stuffExample = new StuffExample();
		// 设置查询条件，材料未被删除的
		stuffExample.createCriteria()
				.andStateNotEqualTo(Constant.STUFF_STATE_2);
		return JSON.toJSONString(stuffService.selectByExample(stuffExample));
	}

	/**
	 * 进入修改材料信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("stuff_update");
		mv.addObject("stuff", stuffService.selectByPrimaryKey(id));
		return mv;
	}

	@RequestMapping(value = "/findByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findByIds(String id) {
		return JSON.toJSONString(stuffService.selectByPrimaryKey(id));
	}

	/**
	 * 修改材料信息
	 * 
	 * @param stuff
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/stuffupdate", produces = "text/html;charset=UTF-8")
	public String stuffupdate(Stuff stuff, MultipartFile file,
			HttpServletRequest request) throws Exception {
		File newFile = new File(fileRootPath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		if (file.getSize() != 0) {
			// 上传文件路径及文件名
			String fileName = FileUtil.executeUpload(newFile.getAbsolutePath()
					+ "/", file);
			stuff.setUrl( "/static/image/"
					+ fileName);
		}
		if (stuffService.updateByPrimaryKeySelective(stuff) > 0) {
			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除材料信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		Stuff stuff = stuffService.selectByPrimaryKey(id);
		stuff.setState(Constant.STUFF_STATE_2);
		if (stuffService.updateByPrimaryKeySelective(stuff) > 0) {
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 批量删除材料信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids) {
		if (ids.length > 0) {
			Stuff stuff = null;
			for (String id : ids) {
				stuff = stuffService.selectByPrimaryKey(id);
				stuff.setState(Constant.STUFF_STATE_2);
				stuffService.updateByPrimaryKeySelective(stuff);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 通过类别查询材料信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findByType", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findByType(String typeId) {
		// 构建查询条件，通过材料类别查询
		StuffExample stuffExample = new StuffExample();
		stuffExample.createCriteria().andTypeIdEqualTo(typeId);
		return JSON.toJSONString(stuffService.selectByExample(stuffExample));
	}

}
