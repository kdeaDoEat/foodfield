package org.kdea.login;

import org.kdea.vo.UserVO;
import org.springframework.security.core.userdetails.User;

public interface LoginDAO {
	public UserVO login(String email);
	public UserVO getUserDetails(String email);
	public int modifyUserInfo(UserVO user);
	public UserVO checkNick(UserVO user);
	public UserVO searchId(UserVO user);
	public UserVO searchPwd(UserVO user);
	public UserVO checkPwd(UserVO user);
	public int tempPwd(UserVO user);
	public int changePwd(UserVO user);
}
