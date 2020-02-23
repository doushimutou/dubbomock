package com.big.tree.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ayt on ${DTAE}
 * just try
 */
public interface ApplicationService {

	ConcurrentHashMap<String, Set<String>> getAllApplications(String keyWords);
}
