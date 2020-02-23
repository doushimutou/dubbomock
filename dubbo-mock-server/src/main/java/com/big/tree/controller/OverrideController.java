package com.big.tree.controller;

import com.big.tree.domain.Override;
import com.big.tree.service.OverriderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 * Author ayt  on
 */
@Controller
@RequestMapping("/override")
public class OverrideController {

	@Resource
	OverriderService overriderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(@RequestBody Override override) {
		overriderService.create(override);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public List<Override> findOverrideByApp(@RequestParam(required = false) String application) {
		return overriderService.findOverrideByApp(application);
	}

	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public void delete(@RequestParam String id ){
		overriderService.delete(id);
	}

	@RequestMapping(value = "/disable",method = RequestMethod.POST)
	public void disableOverride(@RequestParam String id){
		overriderService.disableOverride(id);
	}

	@RequestMapping(value = "/enable",method = RequestMethod.POST)
	public void enableOverride(@RequestParam String id){
		overriderService.enableOverride(id);
	}
}
