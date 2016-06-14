package org.kdea.main;

import java.util.List;

import org.kdea.reviewboard.ReviewService;
import org.kdea.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

	@Autowired
	ReviewService reviewsvc;

	public List<BoardVO> bestreview() {
		// 리뷰중 최고 4개를 뽑는것
		List<BoardVO> list= reviewsvc.getList();
		return null;
	}
	
	
}
