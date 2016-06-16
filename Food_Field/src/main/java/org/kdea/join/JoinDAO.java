package org.kdea.join;

import org.kdea.vo.UserVO;

public interface JoinDAO {
	public int join(UserVO user);
	public int emailAuth(String email);
}
