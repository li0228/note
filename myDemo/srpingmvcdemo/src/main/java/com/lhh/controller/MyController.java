package com.lhh.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lihonghao
 * @date 2021/4/15 16:38
 */
@Controller
public class MyController {
	/**
	 * ModelAndView：
	 * 		Model：数据
	 * 		View：视图
	 */
	@GetMapping(value = "/some.do")
	public ModelAndView dosome(){
		 ModelAndView mv = new ModelAndView();
		 // 添加数据，request作用域
		mv.addObject("msb", "hello");
		mv.addObject("code",200);

		// 指定视图,forward,转发操作
		mv.setViewName("/show.jsp");
		return mv;
	}

	@RequestMapping("/some")
	@ResponseBody
	public String dosome1(){
		return "jkljl";
	}
}
