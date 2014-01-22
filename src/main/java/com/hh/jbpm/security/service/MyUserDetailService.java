package com.hh.jbpm.security.service;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hh.jbpm.security.dao.IUserInfoDao;
import com.hh.jbpm.security.domain.Users;

/*
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 *该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 */
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private IUserInfoDao userInfoDao;
//	@Autowired
//	private UserCache userCache;
	
//	public UserInfoDao getUserInfoDao() {
//		return userInfoDao;
//	}
//
	public void setUserInfoDao(IUserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

//	public UserCache getUserCache() {
//		return userCache;
//	}
//
//	public void setUserCache(UserCache userCache) {
//		this.userCache = userCache;
//	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("username is :" + username);

		Users user = null;
		try {
			user = this.userInfoDao.findByName(username);
			System.out.println(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获得用户权限
		Collection<GrantedAuthority> auths = userInfoDao
				.loadUserAuthorityByName(username);
		

		boolean enables = true;
		// 账户过期否
		boolean accountNonExpired = true;
		// 证书过期否
		boolean credentialsNonExpired = true;
		// 账户锁定否
		boolean accountNonLocked = true;
		// 封装成spring security的user
		User userdetail = new User(username, user.getPassword(), enables,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				auths);
		for (GrantedAuthority s : auths) {
			s.getAuthority();
		}
		System.out.println(auths);
		return userdetail;
	}

}
