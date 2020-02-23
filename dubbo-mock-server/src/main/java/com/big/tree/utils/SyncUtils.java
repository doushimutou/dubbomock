/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.big.tree.utils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.big.tree.domain.Consumer;
import com.big.tree.domain.Provider;
import com.big.tree.domain.Route;
import javafx.util.Pair;
import com.big.tree.domain.Override;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class SyncUtils {

	protected static final Logger logger = LoggerFactory.getLogger(SyncUtils.class);

	public static final String SERVICE_FILTER_KEY = ".service";

	public static final String ADDRESS_FILTER_KEY = ".address";

	public static final String ID_FILTER_KEY = ".id";

	public static Provider url2Provider(Pair<String, URL> pair) {
		if (pair == null) {
			return null;
		}

		String id = pair.getKey();
		URL url = pair.getValue();

		if (url == null) {
			return null;
		}
		Provider p = new Provider();
		p.setId(id);
		p.setService(url.getServiceKey());
		p.setAddress(url.getAddress());
		p.setApplication(url.getParameter(Constants.APPLICATION_KEY));
		p.setUrl(url.toIdentityString());
		p.setParameters(url.toParameterString());

		p.setDynamic(url.getParameter("dynamic", true));
		p.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));
		p.setWeight(url.getParameter(Constants.WEIGHT_KEY, Constants.DEFAULT_WEIGHT));
		p.setUsername(url.getParameter("owner"));

		return p;
	}

	public static List<Provider> url2ProviderList(Map<String, URL> ps) {
		List<Provider> ret = new ArrayList<Provider>();
		for (Map.Entry<String, URL> entry : ps.entrySet()) {
			ret.add(url2Provider(new Pair<String, URL>(entry.getKey(), entry.getValue())));
		}
		return ret;
	}

	public static Consumer url2Consumer(Pair<String, URL> pair) {
		if (pair == null) {
			return null;
		}

		String id = pair.getKey();
		URL url = pair.getValue();

		if (null == url) {
			return null;
		}
		Consumer c = new Consumer();
		c.setId(id);
		c.setService(url.getServiceKey());
		c.setAddress(url.getHost());
		c.setApplication(url.getParameter(Constants.APPLICATION_KEY));
		c.setParameters(url.toParameterString());

		return c;
	}

	public static List<Consumer> url2ConsumerList(Map<String, URL> cs) {
		List<Consumer> list = new ArrayList<>();
		if (cs == null) {
			return list;
		}
		for (Map.Entry<String, URL> entry : cs.entrySet()) {
			list.add(url2Consumer(new Pair<String, URL>(entry.getKey(), entry.getValue())));
		}
		return list;
	}

	public static Route url2Route(Pair<Long, URL> pair) {
		if (pair == null) {
			return null;
		}

		Long id = pair.getKey();
		URL url = pair.getValue();

		if (null == url) {
			return null;
		}
		Route r = new Route();
		r.setId(id);
		r.setName(url.getParameter("name"));
		r.setService(url.getServiceKey());
		r.setPriority(url.getParameter(Constants.PRIORITY_KEY, 0));
		r.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));
		r.setForce(url.getParameter(Constants.FORCE_KEY, false));
		r.setRule(url.getParameterAndDecoded(Constants.RULE_KEY));
		return r;
	}

	public static List<Route> url2RouteList(Map<Long, URL> cs) {
		List<Route> list = new ArrayList<Route>();
		if (cs == null) {
			return list;
		}
		for (Map.Entry<Long, URL> entry : cs.entrySet()) {
			list.add(url2Route(new Pair<Long, URL>(entry.getKey(), entry.getValue())));
		}
		return list;
	}

	public static Override url2Override(Pair<String, URL> pair) {
		if (pair == null) {
			return null;
		}
		String id = pair.getKey();
		URL url = pair.getValue();

		if (null == url) {
			return null;
		}
		Override o = new Override();
		o.setId(id);
		Map<String, String> parameters = new HashMap<String, String>(url.getParameters());
		o.setService(url.getServiceKey());
		parameters.remove(Constants.INTERFACE_KEY);
		parameters.remove(Constants.GROUP_KEY);
		parameters.remove(Constants.VERSION_KEY);
		parameters.remove(Constants.APPLICATION_KEY);
		parameters.remove(Constants.CATEGORY_KEY);
		parameters.remove(Constants.DYNAMIC_KEY);
		parameters.remove(Constants.ENABLED_KEY);

		o.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));

		String host = url.getHost();
		boolean anyhost = url.getParameter(Constants.ANYHOST_VALUE, false);
		if (!anyhost || !"0.0.0.0".equals(host)) {
			o.setAddress(url.getAddress());
		}
		o.setApplication(url.getParameter(Constants.APPLICATION_KEY, url.getUsername()));
		parameters.remove(Constants.VERSION_KEY);
		o.setParams(StringUtils.toQueryString(parameters));

		return o;
	}

	// Map<category, Map<servicename, Map<Long, URL>>>
	public static <SM extends Map<String, Map<String, URL>>> Map<String, URL> filterFromCategory(Map<String, SM> urls, Map<String, String> filter) {
		//获取类别key,value = provider或service或configurators
		String c = (String) filter.get(Constants.CATEGORY_KEY);
		if (c == null) {
			throw new IllegalArgumentException("no category");
		}
		//将provider移除，只剩application或service
		filter.remove(Constants.CATEGORY_KEY);
		logger.info("filter是：{},获取到的类别：{},获取类别的values:{}", filter, c, urls.get(c));
		return filterFromService(urls.get(c), filter);
	}

	public static List<Override> url2OverrideList(Map<String, URL> cs) {
		List<Override> list = new ArrayList<>();
		if (cs == null) {
			return list;
		}
		for (Map.Entry<String, URL> entry : cs.entrySet()) {
			list.add(url2Override(new Pair<String, URL>(entry.getKey(), entry.getValue())));
		}
		return list;
	}


	// Map<servicename, Map<Long, URL>>
	public static Map<String, URL> filterFromService(Map<String, Map<String, URL>> urls, Map<String, String> filter) {
		Map<String, URL> ret = new HashMap<>();
		if (urls == null) {
			return ret;
		}
		//当service搜索时，s有值
		String s = (String) filter.remove(SERVICE_FILTER_KEY);
		logger.info("filter,.setvice:{}", s);
		if (s == null) {
			for (Map.Entry<String, Map<String, URL>> entry : urls.entrySet()) {
				filterFromUrls(entry.getValue(), ret, filter);
			}
		} else {
			//从provider的url中获取key =applicaiton
			//从consumer的url中获取key = service的值
			Map<String, URL> map = urls.get(s);

			//从provider的url中去除数据并翻入到map中
			filterFromUrls(map, ret, filter);
		}
		logger.info("找到的url为：{}", ret);
		return ret;
	}

	// Map<Long, URL>
	static void filterFromUrls(Map<String, URL> from, Map<String, URL> to, Map<String, String> filter) {
		if (from == null || from.isEmpty()) {
			return;
		}
		for (Map.Entry<String, URL> entry : from.entrySet()) {
			URL url = entry.getValue();
			boolean match = true;
			for (Map.Entry<String, String> e : filter.entrySet()) {
				String key = e.getKey();
				String value = e.getValue();
				if (ADDRESS_FILTER_KEY.equals(key)) {
					if (!value.equals(url.getAddress())) {
						match = false;
						break;
					}
				} else {
					if (!value.equals(url.getParameter(key))) {
						match = false;
						break;
					}
				}
			}
			if (match) {
				to.put(entry.getKey(), url);
				logger.info("最终结果为：{}", to);
			}
		}
	}

	public static <SM extends Map<String, Map<String, URL>>> Pair<String, URL> filterFromCategory(Map<String, SM> urls, String category, String id) {
		SM services = urls.get(category);
		if (services == null) {
			return null;
		}

		for (Map.Entry<String, Map<String, URL>> e1 : services.entrySet()) {
			Map<String, URL> u = e1.getValue();
			if (u.containsKey(id)) {
				return new Pair<String, URL>(id, u.get(id));
			}
		}
		return null;
	}
}
