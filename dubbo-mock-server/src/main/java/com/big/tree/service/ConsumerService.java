package com.big.tree.service;

import com.big.tree.domain.Consumer;

import java.util.List;
import java.util.Set;

/**
 * Description
 * Author ayt  on
 */
public interface ConsumerService {

	/**
	 * 获取消费者的应用名
	 * @return
	 */
	Set<String> getApplications();

	List<Consumer> getConsumerByService(String name);
}
