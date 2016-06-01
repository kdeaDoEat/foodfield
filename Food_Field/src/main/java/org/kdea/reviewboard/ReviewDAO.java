package org.kdea.reviewboard;

import java.util.List;

import org.kdea.vo.BoardVO;

public interface ReviewDAO {
	
	public List<BoardVO> getList(int page);
	public int write(BoardVO vo);
	public BoardVO read(int num);
	public int page();

}
