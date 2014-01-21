package com.hh.jbpm.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hh.jbpm.security.domain.Users;

public class UserInfoDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final static Logger log = LoggerFactory
			.getLogger(UserInfoDao.class);
	
	public UserInfoDao(){
		loadSource();
		System.out.println("加载UserInfoDao..." + jdbcTemplate);
	}

	public Users findByName(String username) throws SQLException {
		String sql = "select * from user where username='" + username + "'";
		RowMapper<Users> mapper = new RowMapper<Users>() {
			@Override
			public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
				Users user = new Users();
				user.setId((int) rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setStatus(rs.getInt("status"));
				user.setDescribtion(rs.getString("descn"));
				return user;
			}
		};

		System.out.println("怎么没有呢？");
		Users user = jdbcTemplate.queryForObject(sql, mapper);
		return user;
	}

	// 通过用户名获得权限集合
	public Collection<GrantedAuthority> loadUserAuthorityByName(String username) {
		try{
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			
			List<String> authsList = loadUserAuthorities(username);
			
			for(String roleName:authsList){
//				GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
				auths.add(authority);
			}
			return auths;
		}catch(RuntimeException re){
			log.error("" + re);
			throw re;
		}
	}

	// 获取权限列表
	public List<String> loadUserAuthorities(String username) {
		try {
			String sql = "select r.name as authority "
					+ "from user u join user_role ur on u.id=ur.user_id "
					+ "join role r on r.id=ur.role_id " + "where u.username='"
					+ username + "'";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			List<String> roles = new ArrayList<>();
			for (Map<String, Object> map : list) {
				roles.add((String) map.get("authority"));
			}
			return roles;
		} catch (RuntimeException re) {
			log.error("find by authorities by username failed." + re);
			throw re;
		}
	}
	
	private void loadSource(){
		System.out.println("加载资源");
	}
}
