package com.big.tree.service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.big.tree.domain.Provider;
import com.big.tree.utils.SyncUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Description
 * Author ayt  on
 */
@Service
public class ProviderServiceImpl extends AbstractService implements ProviderService {


	@Override
	public Set<String> getApplications() {
		Set<String> ret = new HashSet<>();
		ConcurrentMap<String, Map<String, URL>> providerUrls = getRegistryCache().get(Constants.PROVIDERS_CATEGORY);
		if (providerUrls == null) {
			return ret;
		}
		for (Map.Entry<String, Map<String, URL>> e1 : providerUrls.entrySet()) {
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
	public List<Provider> getServiceByApplication(String application) {
		return SyncUtils.url2ProviderList(findProviderUrlByApplication(application));

	}

	/**
	 * 通过service获得方法
	 *
	 * @param service
	 * @return
	 */
	@Override
	public List<String> getMethodsByService(String service) {
		List<String> ret = new ArrayList<>();
		ConcurrentMap<String, Map<String, URL>> providerUrls = getRegistryCache().get(Constants.PROVIDERS_CATEGORY);
		if (providerUrls == null || service == null || service.length() == 0) {
			return ret;
		}
		Map<String, URL> providers = providerUrls.get(service);
		if (providers == null || providers.isEmpty()) {
			return ret;
		}

		List<String> finalRet = ret;
		providers.forEach((s, url) ->{
			String value = url.getParameter("methods");
			finalRet.add(value);
		});
		return finalRet;

//		Map.Entry<String, URL> p = providers.entrySet().iterator().next();
//		String value = p.getValue().getParameter("methods");
//
//		if (value == null || value.length() == 0) {
//			return ret;
//		}
//		String[] methods = value.split(",");
//		if (methods == null || methods.length == 0) {
//			return ret;
//		}
//		ret = Arrays.stream(methods).collect(Collectors.toList());
//		return ret;
	}

	/**
	 * 通过应用名称查找provider
	 *
	 * @param application
	 * @return
	 */
	private Map<String, URL> findProviderUrlByApplication(String application) {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY);
		filter.put(Constants.APPLICATION_KEY, application);
		return SyncUtils.filterFromCategory(getRegistryCache(), filter);
	}
}
