package org.kdea.ranking;

import java.util.ArrayList;
import java.util.List;

import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

	final int rpp = 10;
	final int ppp = 5;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	RankingDAO dao;
	
	public List<UserVO> getList() {

		List<UserVO> userlist = new ArrayList<UserVO>();
		dao = sqlSessionTemplate.getMapper(RankingDAO.class);
		userlist = dao.getList();
		return userlist;
	}

}
