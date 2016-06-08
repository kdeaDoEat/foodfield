package org.kdea.login;

import org.kdea.vo.UserVO;

public interface LoginDAO {
	public UserVO login(String email);
}
