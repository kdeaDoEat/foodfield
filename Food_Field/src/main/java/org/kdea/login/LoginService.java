package org.kdea.login;

import javax.servlet.http.HttpSession;

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
	
	public UserVO login(String email) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO user = dao.login(email);
		return user;
	}
	
	public UserVO authUser(String email, String pwd) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO user = dao.login(email);
		if(passwordEncoder.matches(pwd, user.getPwd()))
			return user;
		return null;
	}
	
	public boolean modifyUserInfo(UserVO user, HttpSession session) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		if(dao.modifyUserInfo(user) > 0) {
			session.setAttribute("userInfo", dao.login(user.getEmail()));
			return true;
		}
		return false;
//		return dao.modifyUserInfo(user) > 0 ? true : false;
	}
	
	public boolean checkNick(UserVO uservo) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO user = dao.checkNick(uservo);
		if(user == null)
			return false;
		return true;
	}
}
