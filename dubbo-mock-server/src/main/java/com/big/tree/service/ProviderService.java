package com.big.tree.service;

import com.big.tree.domain.Provider;

import java.util.List;
import java.util.Set;

/**
 * Created by ayt on ${DTAE}
 * just try
 */
public interface ProviderService {
	/**
	 * 获取提供者的应用名
	 * @return
	 */
	Set<String> getApplications();

	/**
	 * 通过应用名字获取provider
	 * @return
	 */
	List<Provider> getServiceByApplication(String application);

	/**
	 * 通过service获得方法
	 * @param service
	 * @return
	 */
	List<String> getMethodsByService(String service);

}
