package com.troika.emall.oauth2;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.troika.emall.model.TMallUser;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		TMallUser tMallUser = entityManager.find(TMallUser.class, 8);
		if (tMallUser == null) throw new UsernameNotFoundException("Couldn't find the user " + username);
		return new User(tMallUser.getUserName(), tMallUser.getPassword(),
				AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"));
	}

}
