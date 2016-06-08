package org.kdea.join;

import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean join(UserVO user) {
		JoinDAO dao = sqlSessionTemplate.getMapper(JoinDAO.class);
		String encodedPwd = passwordEncoder.encode(user.getPwd());
		user.setPwd(encodedPwd);
		if(dao.join(user) > 0) {
			return true;
		}
		return false;
	}
}
