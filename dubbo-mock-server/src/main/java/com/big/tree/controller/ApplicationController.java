package com.big.tree.controller;

import com.big.tree.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description
 * Author ayt  on
 */
@Controller
@RequestMapping("/app")
public class ApplicationController {

	@Resource
	ApplicationService applicationService;
	@RequestMapping(value = "/apps",method = RequestMethod.GET)
	@ResponseBody
	public ConcurrentHashMap<String, Set<String>> allApplications(@RequestParam(required = false) String name){
		return applicationService.getAllApplications(name);
	}

}
