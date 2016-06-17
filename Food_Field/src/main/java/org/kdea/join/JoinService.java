package org.kdea.join;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.kdea.login.LoginDAO;
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
	
	public boolean sendPwd(String email, String name) throws Exception {
		try {
			String newPwd = "";
			for (int i = 1; i <= 8; i++) {
				char ch = (char) ((Math.random() * 26) + 65);
				newPwd += ch;
			}
			
			String encodedPwd = passwordEncoder.encode(newPwd);
			LoginDAO dao = sqlSessionTemplate.getMapper(LoginDAO.class);
			UserVO user = new UserVO();
			user.setEmail(email);
			user.setPwd(encodedPwd);
			dao.tempPwd(user);
			
			EmailVO emailVO = new EmailVO();
			String receiver = email; //Receiver.
	        String subject = name + "님 임시비밀번호를 발급하였습니다.";
	        String content = "회원님의 임시 비밀번호는 " + newPwd + " 입니다.\n 비밀번호를 꼭 변경해주세요 !";
	         
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
}
