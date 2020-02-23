package com.big.tree.service;

import com.alibaba.dubbo.common.URL;

import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryService;
import com.big.tree.registry.RegistryServerSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Description
 * Author ayt  on
 */
@Component
public class AbstractService {


	protected static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	protected RegistryService registryService;

	@Autowired
	private RegistryServerSync sync;

	public ConcurrentMap<String, ConcurrentMap<String, Map<String, URL>>> getRegistryCache() {
		return sync.getRegistryCache();
	}

}
