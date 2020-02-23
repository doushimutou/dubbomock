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
package com.big.tree.domain;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.Map;

public class Override {

	private static final long serialVersionUID = 114828505391757846L;

	private String id;

	private String service;

	private String params;

	private String application;

	private String address;

	private String username;

	private boolean enabled;

	public Override() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public String getParams() {
		return params;
	}


	public void setParams(String params) {
		this.params = params;
	}


	public String getApplication() {
		return application;
	}


	public void setApplication(String application) {
		this.application = application;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public String toString() {
		return "Override [service=" + service + ", params=" + params + ", application="
				+ application + ", address=" + address + ", username=" + username + ", enabled=" + enabled + "]";
	}

	public boolean isDefault() {
		return (getAddress() == null || getAddress().length() == 0 || Constants.ANY_VALUE.equals(getAddress()) || Constants.ANYHOST_VALUE.equals(getAddress()))
				&& (getApplication() == null || getApplication().length() == 0 || Constants.ANY_VALUE.equals(getApplication()));
	}

	public boolean isMatch(String service, String address, String application) {
		return isEnabled() && getParams() != null && getParams().length() > 0
				&& service.equals(getService())
				&& (address == null || getAddress() == null || getAddress().length() == 0 || getAddress().equals(Constants.ANY_VALUE) || getAddress().equals(Constants.ANYHOST_VALUE) || getAddress().equals(address))
				&& (application == null || getApplication() == null || getApplication().length() == 0 || getApplication().equals(Constants.ANY_VALUE) || getApplication().equals(application));
	}

	public boolean isUniqueMatch(Provider provider) {
		return isEnabled() && getParams() != null && getParams().length() > 0
				&& provider.getService().equals(getService())
				&& provider.getAddress().equals(getAddress());
	}

	public boolean isMatch(Provider provider) {
		return isEnabled() && getParams() != null && getParams().length() > 0
				&& provider.getService().equals(getService())
				&& (getAddress() == null || getAddress().length() == 0 || getAddress().equals(Constants.ANY_VALUE) || getAddress().equals(Constants.ANYHOST_VALUE) || getAddress().equals(provider.getAddress()))
				&& (getApplication() == null || getApplication().length() == 0 || getApplication().equals(Constants.ANY_VALUE) || getApplication().equals(provider.getApplication()));
	}

	public boolean isUniqueMatch(Consumer consumer) {
		return isEnabled() && getParams() != null && getParams().length() > 0
				&& consumer.getService().equals(getService())
				&& consumer.getAddress().equals(getAddress());
	}

	public boolean isMatch(Consumer consumer) {
		return isEnabled() && getParams() != null && getParams().length() > 0
				&& consumer.getService().equals(getService())
				&& (getAddress() == null || getAddress().length() == 0 || getAddress().equals(Constants.ANY_VALUE) || getAddress().equals(Constants.ANYHOST_VALUE) || getAddress().equals(consumer.getAddress()))
				&& (getApplication() == null || getApplication().length() == 0 || getApplication().equals(Constants.ANY_VALUE) || getApplication().equals(consumer.getApplication()));
	}

	public Map<String, String> toParametersMap() {
		Map<String, String> map = StringUtils.parseQueryString(getParams());
		map.remove(Constants.INTERFACE_KEY);
		map.remove(Constants.GROUP_KEY);
		map.remove(Constants.VERSION_KEY);
		map.remove(Constants.APPLICATION_KEY);
		map.remove(Constants.CATEGORY_KEY);
		map.remove(Constants.DYNAMIC_KEY);
		map.remove(Constants.ENABLED_KEY);
		return map;
	}

	public URL toUrl() {
		String group = null;
		String version = null;
		//服务
		String path = service;

		int i = path.indexOf("/");
		//区分group和path
		if (i > 0) {
			group = path.substring(0, i);
			path = path.substring(i + 1);
		}
		//区分初版本号
		i = path.lastIndexOf(":");
		if (i > 0) {
			version = path.substring(i + 1);
			path = path.substring(0, i);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.OVERRIDE_PROTOCOL);
		sb.append("://");
		if (!StringUtils.isBlank(address) && !Constants.ANY_VALUE.equals(address)) {
			sb.append(address);
		} else {
			//address为空默认为全部
			sb.append(Constants.ANYHOST_VALUE);
		}
		sb.append("/");
		sb.append(path);
		sb.append("?");
		Map<String, String> param = StringUtils.parseQueryString(params);
		param.put(Constants.CATEGORY_KEY, Constants.CONFIGURATORS_CATEGORY);
		//设置是否启用
		param.put(Constants.ENABLED_KEY, String.valueOf(isEnabled()));
		//设置dynamic = false
		param.put(Constants.DYNAMIC_KEY, "false");
		if (!StringUtils.isBlank(application) && !Constants.ANY_VALUE.equals(application)) {
			//设置应用
			param.put(Constants.APPLICATION_KEY, application);
		}
		if (group != null) {
			//设置group
			param.put(Constants.GROUP_KEY, group);
		}
		if (version != null) {
			//设置版本
			param.put(Constants.VERSION_KEY, version);
		}
		//将param 转换成查询字符串
		sb.append(StringUtils.toQueryString(param));
		//将string 转换成url
		return URL.valueOf(sb.toString());
	}

}
