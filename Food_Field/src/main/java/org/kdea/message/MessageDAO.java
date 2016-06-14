package org.kdea.message;

import java.util.List;

import org.kdea.vo.MessageVO;
import org.kdea.vo.SearchVO;
import org.kdea.vo.UserVO;

public interface MessageDAO {

	public int getMessageCount(UserVO vo);
	public List<MessageVO> getMessageList(MessageVO mvo);
	public List<UserVO> getMemberInfo(UserVO vo);
	public int sendMsg(MessageVO vo);
	public MessageVO getMessageContents(MessageVO vo);
	public int readOk(MessageVO vo);
	public List<MessageVO> getsendList(MessageVO mvo);
	public int getsendListpage(UserVO vo);
	public int getListpage(UserVO vo);
	public int getSearchListpage(SearchVO vo);
	public List<MessageVO> getSearchList(SearchVO vo);
	public int getSendSearchListpage(SearchVO vo);
	public List<MessageVO> getSendSearchList(SearchVO vo);
	public int deleteMessage(MessageVO vo);

}
