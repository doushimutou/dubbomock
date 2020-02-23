package com.big.tree.service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.big.tree.domain.Consumer;
import com.big.tree.utils.SyncUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * Description
 * Author ayt  on
 */
@Service
public class ConsumerServiceImpl extends AbstractService implements ConsumerService {

	@Override
	public Set<String> getApplications() {
		Set<String> ret = new HashSet<>();
		ConcurrentMap<String, Map<String, URL>> consumerUrls = getRegistryCache().get(Constants.CONSUMERS_CATEGORY);
		if (consumerUrls == null) {
			return ret;
		}
		for (Map.Entry<String, Map<String, URL>> e1 : consumerUrls.entrySet()) {
			Map<String, URL> value = e1.getValue();
			for (Map.Entry<String, URL> e2 : value.entrySet()) {
				URL u = e2.getValue();
				String app = u.getParameter(Constants.APPLICATION_KEY);
				if (app != null) {
					ret.add(app);
				}
			}
		}
		return ret;
	}

	@Override
	public List<Consumer> getConsumerByService(String name) {
		return SyncUtils.url2ConsumerList(findConsumerUrlByService(name));
	}

	/**
	 * 通过应用名称查找consumer
	 * @param service 服务名称
	 * @return
	 */
	private Map<String, URL> findConsumerUrlByService(String service) {
		service = "com.dianwoba.dispatch.inverse.monitor.provider.DrillRuleProvider:1.0.0";
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.CONSUMERS_CATEGORY);
		filter.put(SyncUtils.SERVICE_FILTER_KEY, service);
		return SyncUtils.filterFromCategory( getRegistryCache(), filter);
	}

}
