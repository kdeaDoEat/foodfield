package org.kdea.login;

import org.kdea.vo.UserVO;
import org.springframework.security.core.userdetails.User;

public interface LoginDAO {
	public UserVO login(String email);
	public UserVO getUserDetails(String email);
	public int modifyUserInfo(UserVO user);
	public UserVO checkNick(UserVO user);
}
