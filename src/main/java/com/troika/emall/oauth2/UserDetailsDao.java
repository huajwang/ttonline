package com.troika.emall.oauth2;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsDao {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
