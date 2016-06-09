package org.kdea.login;

import java.util.ArrayList;
import java.util.List;

import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserVO login(String email) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO user = dao.login(email);
//		boolean result = passwordEncoder.matches(user.getPwd(), userDB.getPwd());
//		if(result) {
		return user;
//		}
//		return null;
	}
	
	public UserVO getUser(String email) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO user = dao.login(email);
		return user;
	}
}
