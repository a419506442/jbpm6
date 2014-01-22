package com.hh.jbpm.security.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.hh.jbpm.security.domain.Users;

public interface IUserInfoDao {

	public Users findByName(String username) throws SQLException;
	public Collection<GrantedAuthority> loadUserAuthorityByName(String username) ;
	public List<String> loadUserAuthorities(String username);
}
