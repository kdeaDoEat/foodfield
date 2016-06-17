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
	
	public UserVO searchId(UserVO user) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO searchUser = dao.searchId(user);
		if(searchUser == null)
			return null;
		return searchUser;
	}
	
	public UserVO searchPwd(UserVO user) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO searchUser = dao.searchPwd(user);
		if(searchUser == null)
			return null;
		return searchUser;
	}
	
	public boolean changePwd(String email, String oldPwd, String newPwd) {
		LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
		UserVO user = new UserVO();
		user.setEmail(email);
		user = dao.login(email);
		if(!passwordEncoder.matches(oldPwd, user.getPwd())) {
			System.out.println("에러 1" + user.getPwd());
			return false;
		}
		newPwd = passwordEncoder.encode(newPwd);
		user.setPwd(newPwd);
		if(dao.changePwd(user) == 0) {
			System.out.println("에러 2" + user.getPwd());
			return false;
		}
		return true;
	}
}
