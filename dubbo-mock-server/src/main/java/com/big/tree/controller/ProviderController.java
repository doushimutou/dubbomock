package com.big.tree.controller;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.big.tree.domain.Provider;
import com.big.tree.service.AbstractService;
import com.big.tree.service.ProviderService;
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
@RequestMapping("/provider")
@Controller
public class ProviderController {

	@Resource
	AbstractService abstractService;
	@Resource
	ProviderService providerService;

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	@ResponseBody
	public Set<String> findApplications() {
		return null;
	}

	@RequestMapping(value = "/services", method = RequestMethod.GET)
	@ResponseBody
	public List<Provider> findServicesByApp(@RequestParam(required = false) String app) {
		return providerService.getServiceByApplication(app);
	}

	@RequestMapping(value = "/methods", method = RequestMethod.GET)
	@ResponseBody
	public List<String> findMethodsByService(@RequestParam(required = false) String service) {
		return providerService.getMethodsByService(service);
	}

}
