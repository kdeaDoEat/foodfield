package org.kdea.chatting;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kdea.vo.UserVO;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class ChattingInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		System.out.println("Before Handshake");

		ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
		System.out.println("URI:" + request.getURI());

		HttpServletRequest req = ssreq.getServletRequest();

		if (req.getSession().getAttribute("userInfo") == null) {

			System.out.println("채팅: 로그인 안하셨습니다.");
			return false;
		}

		System.out.println("param, id:" + ((UserVO) req.getSession().getAttribute("userInfo")).getNickname());
		System.out.println(req.getParameter("_csrf"));

		String usrId = ((UserVO) req.getSession().getAttribute("userInfo")).getNickname();
		attributes.put("usrId", usrId);

		return super.beforeHandshake(request, response, wsHandler, attributes);

	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
		HttpServletRequest req = ssreq.getServletRequest();
		System.out.println(
				"After Handshake, id: 접속자 : " + ((UserVO) req.getSession().getAttribute("userInfo")).getNickname());
		super.afterHandshake(request, response, wsHandler, ex);
	}

}
