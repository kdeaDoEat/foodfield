package org.kdea.join;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.kdea.vo.EmailVO;
import org.kdea.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private JavaMailSender mailSender;
	
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
	
	public boolean sendMail(String email) throws Exception {
		try {
			EmailVO emailVO = new EmailVO();
			String receiver = email; //Receiver.
	        String subject = "회원가입을 축하드립니다 !";
	        String content = "http://192.168.8.28:8088/FoodField/emailAuth?email=" + email;
	         
	        emailVO.setReceiver(receiver);
	        emailVO.setSubject(subject);
	        emailVO.setContent(content);
	        
			MimeMessage msg = mailSender.createMimeMessage();
			msg.setFrom("someone@paran.com"); // 송신자를 설정해도 소용없지만 없으면 오류가 발생한다
			msg.setSubject(emailVO.getSubject());
			msg.setText(emailVO.getContent());
			msg.setRecipient(RecipientType.TO, new InternetAddress(emailVO.getReceiver()));

			mailSender.send(msg);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean emailAuth(String email) {
		JoinDAO dao = sqlSessionTemplate.getMapper(JoinDAO.class);
		return dao.emailAuth(email) > 0 ? true : false;
	}
}
