package com.big.tree.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Description
 * Author ayt  on
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

	public static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Resource
	ProviderService providerService;
	@Resource
	ConsumerService consumerService;

	@Override
	public ConcurrentHashMap<String, Set<String>> getAllApplications(String name) {
		ConcurrentHashMap<String, Set<String>> result = new ConcurrentHashMap<>();

		Set<String> providers = providerService.getApplications();
		Set<String> consumers = consumerService.getApplications();
		Set<String> applications = new TreeSet<>();
		if (providers != null && providers.size() > 0) {
			applications.addAll(providers);
			result.put("providers", providers);
		}
		if (consumers != null && consumers.size() > 0) {
			applications.addAll(consumers);
			result.put("consumers", consumers);
		}
		result.put("applications", applications);
		logger.info("applications-befroe:{}", applications);
		if (!StringUtils.isEmpty(name)) {
			result.clear();
			String finalName = name.toLowerCase();
			Set<String> appResult = applications.stream().filter(s -> s.toLowerCase().contains(finalName)).collect(Collectors.toSet());
			result.put("applications", appResult);
			logger.info("applications-filter:{}", applications);
			Set<String> proResult = providers.stream().filter(s -> s.toLowerCase().contains(finalName)).collect(Collectors.toSet());
			result.put("providers", proResult);
			Set<String> conResult = consumers.stream().filter(s -> s.toLowerCase().contains(finalName)).collect(Collectors.toSet());
			result.put("consumers", conResult);
			return result;
		}
		return result;
	}
}
