package org.kdea.chatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class ChattingService {
    
	/*맴버변수로 연결 후 autowired 하면 갱신 안됨*/
	
	public void broadcast(WebSocketSession session, TextMessage message, Map<String,WebSocketSession> users){
		
        for (WebSocketSession s : users.values()) {
            Map<String, Object> map = s.getAttributes();
            String userId = (String) map.get("usrId");
            System.out.println("message 보내는 중인 interceptor에서 건너온 ID " + userId);
            if (message.getPayload().equals("/userrequest")) {

               List<String> userlist = new ArrayList<String>();
               Iterator usernames = users.keySet().iterator();
               String newsend = "/userrequest/";
               while (usernames.hasNext()) {

                  System.out.println("userlist while문");
                  String user = (String) usernames.next();
                  System.out.println(user);
                  userlist.add(user);
                  newsend += user + "/";

               }
               try {
				s.sendMessage(new TextMessage(newsend));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            } else {

               String[] msgarray = message.getPayload().split("/");
               for (int i = 0; i < msgarray.length; i++) {

                  System.out.println("메세지 분리 합니다~" + i + msgarray[i]);

               }
               
               Map<String, Object> mymap = session.getAttributes();
               String myId = (String) mymap.get("usrId");
                             
               //session.sendMessage(new TextMessage(myId+":"+msgarray[msgarray.length - 1]));
               List<String> userlist = new ArrayList<String>();
               Iterator usernames = users.keySet().iterator();
               while (usernames.hasNext()) {
                  String user = (String) usernames.next();
                  for (int i = 0; i < msgarray.length - 1; i++) {
                          if (user.equals(msgarray[i])) {
                        WebSocketSession ss = users.get(user);
                        if(s!=ss){
                        try {
							ss.sendMessage(new TextMessage(myId+":"+msgarray[msgarray.length - 1]));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        }
                     }
                  }
               }

            }
        }
		
		
	}
	
}
