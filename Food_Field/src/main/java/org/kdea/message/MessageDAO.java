package org.kdea.message;

import java.util.List;

import org.kdea.vo.MessageVO;
import org.kdea.vo.UserVO;

public interface MessageDAO {

	public int getMessageCount(UserVO vo);
	public List<MessageVO> getMessageList(UserVO vo);
	public List<UserVO> getMemberInfo(UserVO vo);
	public int sendMsg(MessageVO vo);
	public MessageVO getMessageContents(MessageVO vo);

}
