package com.hh.jbpm.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
//		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
//		GrantedAuthorityImpl auth2 = new GrantedAuthorityImpl("ROLE_admin");
		return null;
	}

}
