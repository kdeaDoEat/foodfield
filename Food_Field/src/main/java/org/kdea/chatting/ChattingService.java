package org.kdea.chatting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class ChattingService {

	/* ¸É¹öº¯¼ö·Î ¿¬°á ÈÄ autowired ÇÏ¸é °»½Å ¾ÈµÊ */

	public void broadcast(WebSocketSession session, TextMessage message, Map<String, WebSocketSession> users) {

		try {

			Map<String, Object> mymap = session.getAttributes();
			String myId = (String) mymap.get("usrId");
			String content = message.getPayload();
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(content);
			jo.put("msg", myId + ":" + (String) jo.get("msg"));
			String status = (String) jo.get("status");

			for (WebSocketSession s : users.values()) {
				Map<String, Object> map = s.getAttributes();
				String userId = (String) map.get("usrId");

				if (status.equals("userrequest")) {

					List<String> userlist = new ArrayList<String>();
					Iterator usernames = users.keySet().iterator();
					while (usernames.hasNext()) {

						String user = (String) usernames.next();
						userlist.add(user);

					}
					jo.put("sender", myId);
					jo.put("recievers", userlist);

					s.sendMessage(new TextMessage(jo.toJSONString()));

					if (session != s) {
						JSONObject obj = new JSONObject();
						obj.put("status", "sendrequest");
						obj.put("msg", myId + "´Ô²²¼­ Á¢¼ÓÇÏ¼Ì½À´Ï´Ù~");
						s.sendMessage(new TextMessage(obj.toJSONString()));
					}
					
				} else if (status.equals("sendrequest")) {

					List<String> userlist = (List<String>) jo.get("recievers");
					Iterator usernames = users.keySet().iterator();
					while (usernames.hasNext()) {
						String user = (String) usernames.next();
						for (int i = 0; i < userlist.size(); i++) {
							if (user.equals((String) userlist.get(i))) {
								WebSocketSession ss = users.get(user);
								if (s != ss) {
									
									ss.sendMessage(new TextMessage(jo.toJSONString()));

								}
							}
						}
					}

				}
			}

			if (status.equals("userrequest")) {
				
				JSONObject obj = new JSONObject();
				obj.put("status", "sendrequest");
				obj.put("msg", myId + "´Ô²²¼­ Á¢¼ÓÇÏ¼Ì½À´Ï´Ù~");
				session.sendMessage(new TextMessage(obj.toJSONString()));
				
			}
			if (status.equals("sendrequest")) {
				
				session.sendMessage(new TextMessage(jo.toJSONString()));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public void leaveBroadcast(WebSocketSession session, Map<String, WebSocketSession> users) {
		try {
			
			Map<String, Object> mymap = session.getAttributes();
			String myId = (String) mymap.get("usrId");
			
			for (WebSocketSession s : users.values()) {
				
				List<String> userlist = new ArrayList<String>();
				Iterator usernames = users.keySet().iterator();
				JSONObject jo = new JSONObject();
				while (usernames.hasNext()) {

					String user = (String) usernames.next();
					userlist.add(user);

				}
				
				jo.put("sender", myId);
				jo.put("status", "userrequest");
				jo.put("recievers", userlist);

				s.sendMessage(new TextMessage(jo.toJSONString()));
				if (session != s) {
					
					JSONObject obj = new JSONObject();
					obj.put("status", "sendrequest");
					obj.put("msg", myId + "´Ô²²¼­ ÅðÀåÇÏ¼Ì½À´Ï´Ù~");
					s.sendMessage(new TextMessage(obj.toJSONString()));
					
				}
				
			}
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
