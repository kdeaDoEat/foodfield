package org.kdea.login;

import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserVO login(UserVO user) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO userDB = dao.login(user.getEmail());
		boolean result = passwordEncoder.matches(user.getPwd(), userDB.getPwd());

		if(result) {
			return user;
		}
		return null;
	}
}
