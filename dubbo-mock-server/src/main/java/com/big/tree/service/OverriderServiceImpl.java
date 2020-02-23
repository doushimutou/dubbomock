package com.big.tree.service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.big.tree.domain.Override;
import com.big.tree.utils.SyncUtils;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * Author ayt  on
 */
@Component
public class OverriderServiceImpl extends AbstractService implements OverriderService {

	@java.lang.Override
	public void create(Override override) {
		URL url = override.toUrl();
		registryService.register(url);
	}

	@java.lang.Override
	public void delete(String id) {
		URL url = findOverrideUrl(id);
		logger.info("通过Id找到的动态配置的URL为：{}",url);
		registryService.unregister(url);
	}

	@java.lang.Override
	public void enableOverride(String id) {
		if (id == null) {
			throw new IllegalStateException("no override id");
		}

		URL oldOverride = findOverrideUrl(id);
		if (oldOverride == null) {
			throw new IllegalStateException("Override was changed!");
		}
		if (oldOverride.getParameter("enabled", true)) {
			return;
		}

		URL newOverride = oldOverride.addParameter("enabled", "true");
		registryService.unregister(oldOverride);
		registryService.register(newOverride);

	}

	@java.lang.Override
	public void disableOverride(String id) {
		if (id == null) {
			throw new IllegalStateException("no override id");
		}

		URL oldProvider = findOverrideUrl(id);
		if (oldProvider == null) {
			throw new IllegalStateException("Override was changed!");
		}
		if (!oldProvider.getParameter("enabled", true)) {
			return;
		}
		URL newProvider = oldProvider.addParameter("enabled", false);
		registryService.unregister(oldProvider);
		registryService.register(newProvider);

	}
	/**
	 * 通过应用名查找动态配置
	 *
	 * @param appName
	 * @return
	 */
	@java.lang.Override
	public List<Override> findOverrideByApp(String appName) {
		return SyncUtils.url2OverrideList(findOverrideUrl(null, null, appName));
	}

	private Map<String, URL> findOverrideUrl(String service, String address, String appName) {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.CONFIGURATORS_CATEGORY);

		if (service != null && service.length() > 0) {
			filter.put(SyncUtils.SERVICE_FILTER_KEY, service);
		}
		if (address != null && address.length() > 0) {
			filter.put(SyncUtils.ADDRESS_FILTER_KEY, address);
		}
		if (appName != null && appName.length() > 0) {
			filter.put(Constants.APPLICATION_KEY, appName);
		}

		return SyncUtils.filterFromCategory(getRegistryCache(), filter);
	}


	URL findOverrideUrl(String id) {
		return findById(id).toUrl();
	}

	public Override findById(String id) {
		return SyncUtils.url2Override(findOverrideUrlPair(id));
	}

	private Pair<String, URL> findOverrideUrlPair(String id) {
		return SyncUtils.filterFromCategory(getRegistryCache(), Constants.CONFIGURATORS_CATEGORY, id);
	}
}
