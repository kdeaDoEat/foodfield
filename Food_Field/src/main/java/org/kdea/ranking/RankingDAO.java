package org.kdea.ranking;

import java.util.List;

import org.kdea.vo.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingDAO {

	public List<UserVO> getList();
	
}
