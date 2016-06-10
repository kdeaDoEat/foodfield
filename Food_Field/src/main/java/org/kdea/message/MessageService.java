package org.kdea.message;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.kdea.vo.MessageVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // 설정파일에 빈으로 등록되었기 때문에 생성자나 Setter 없이 자동으로 주입

	public int messageCount(HttpSession session) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO vo = (UserVO)session.getAttribute("userInfo");
		return dao.getMessageCount(vo);
	}

	public List<MessageVO> getMessageList(HttpSession session) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		UserVO vo = (UserVO)session.getAttribute("userInfo");
		return dao.getMessageList(vo);
	}

	public List<UserVO> searchMember(UserVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		List<UserVO> luvo = dao.getMemberInfo(vo);
		if(luvo.size() == 0){
			UserVO uvo = new UserVO();
			uvo.setEmail("");
			uvo.setNickname("검색결과 없음");
			uvo.setGender("");
			luvo.add(uvo);
		}
		return luvo;
	}

	public String sendMsg(MessageVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		JSONObject jobj = new JSONObject();
		if(dao.sendMsg(vo)>0){
			jobj.put("ok", true);
		}else{
			jobj.put("ok", false);
		}
		return jobj.toJSONString();
	}

	public MessageVO getMessageContents(MessageVO vo) {
		MessageDAO dao = sqlSessionTemplate.getMapper(MessageDAO.class);
		return dao.getMessageContents(vo);
	}
	

}
