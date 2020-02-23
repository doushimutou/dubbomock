package com.big.tree.controller;


import com.big.tree.domain.Consumer;
import com.big.tree.service.AbstractService;
import com.big.tree.service.ConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Description
 * Author ayt  on
 */
@Controller
@RequestMapping("/consumer")
public class ConsumerController {

	@Resource
	AbstractService abstractService;
	@Resource
	ConsumerService consumerService;

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	@ResponseBody
	public Set<String> findApplications() {
		return null;
	}

	@RequestMapping(value = "/byservice", method = RequestMethod.GET)
	@ResponseBody
	public List<Consumer> getConsumerByService(@RequestParam(required = false) String name) {
		return consumerService.getConsumerByService(name);
	}

	;
}
