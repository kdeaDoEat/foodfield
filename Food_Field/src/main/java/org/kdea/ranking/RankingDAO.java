package org.kdea.ranking;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.kdea.vo.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingDAO {
	
	public int getAllCount(@Param("option") String option, @Param("search") String search);
	public List<UserVO> getList(@Param("page") int page, @Param("rpp") int rpp, @Param("option") String option, @Param("search") String search);
	
}
