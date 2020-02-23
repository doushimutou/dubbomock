package com.big.tree.service;

import com.big.tree.domain.Override;

import java.util.List;

/**
 * Description
 * Author ayt  on
 */
public interface OverriderService {
	/**
	 * 新增一个动态配置
	 * @param override
	 */
	void create(Override override);

	/**
	 * 删除一条动态配置
	 * @param id
	 */
	void delete(String id);

	/**
	 * 通过应用名查找动态配置
	 * @param appName
	 * @return
	 */
	List<Override> findOverrideByApp(String appName);

	/**
	 * 通过id 禁用配置
	 * @param id
	 */
	void disableOverride(String id);
	/**
	 * 通过id 启用配置
	 * @param id
	 */
	void enableOverride(String id);

}
