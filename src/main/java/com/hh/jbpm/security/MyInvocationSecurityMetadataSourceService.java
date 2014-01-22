package com.hh.jbpm.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.hh.jbpm.security.dao.ResourcesDao;
import com.hh.jbpm.security.domain.Resource;

public class MyInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	private ResourcesDao resourcesDao;
	// resourceMap及为key-url，value-Collection<ConfigAttribute>,资源权限对应Map
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	// public ResourcesDao getResourcesDao() {
	// return resourcesDao;
	// }
	//
	// public void setResourcesDao(ResourcesDao resourcesDao) {
	// this.resourcesDao = resourcesDao;
	// System.out.println("加载MyInvocationSecurityMetadataSourceService..."
	// + resourcesDao);
	// loadResourceDefine();
	// }

	public MyInvocationSecurityMetadataSourceService() {

	}

	public MyInvocationSecurityMetadataSourceService(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
		System.out.println("加载MyInvocationSecurityMetadataSourceService..."
				+ resourcesDao);
		loadResourceDefine();
	}

	// 加载所有资源与权限的关系
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resource> resources = resourcesDao.findAll();
			// 加载资源对应的权限
			for (Resource resource : resources) {
				Collection<ConfigAttribute> auths = resourcesDao
						.loadRoleByResource(resource.getRes_string());
				System.out.println("权限=" + auths);
				resourceMap.put(resource.getRes_string(), auths);
			}
		}
		// List<String> permissionList = new ArrayList<String>();
		// resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		// Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		// for (String auth : permissionList) {
		// ConfigAttribute ca = new SecurityConfig(auth);// "ROLE_ADMIN"
		// List<String> resourceList = new ArrayList<String>();
		// for (String res : resourceList) {
		// String url = res;
		// // 判断资源文件和权限的对应关系，如果已经存在，要进行增加
		// if (resourceMap.containsKey(url)) {
		// Collection<ConfigAttribute> value = resourceMap.get(url);
		// value.add(ca);
		// resourceMap.put(url, value);
		// // "log.jsp","role_user,role_admin"
		// } else {
		// atts.add(ca);
		// resourceMap.put(url, atts);
		// }
		// resourceMap.put(url, atts);
		// }
		// }
	}

	// 加载所有资源与权限的关系
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// object是一个URL，被用户请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);

		int firstQuestionMarkIndex = requestUrl.indexOf("?");
		if (firstQuestionMarkIndex != -1) {
			requestUrl = requestUrl.substring(0, firstQuestionMarkIndex);
		}
		if (resourceMap == null) {
			loadResourceDefine();
		}
		//
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();

			if (resURL.equals(requestUrl)) {
				return resourceMap.get(resURL);
			}
		}
		return null;

		// // guess object is a URL.
		// String url = ((FilterInvocation) object).getRequestUrl();
		// Iterator<String> ite = resourceMap.keySet().iterator();
		// while (ite.hasNext()) {
		// String resURL = ite.next();
		// if (urlMatcher.pathMatchesUrl(url, resURL)) {
		// return resourceMap.get(resURL);
		// }
		// }
		// return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
