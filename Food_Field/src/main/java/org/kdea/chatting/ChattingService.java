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
    
	/*맴버변수로 연결 후 autowired 하면 갱신 안됨*/
	
	public void broadcast(WebSocketSession session, TextMessage message, Map<String,WebSocketSession> users){
		
		try{
		
        for (WebSocketSession s : users.values()) {
            Map<String, Object> map = s.getAttributes();
            String userId = (String) map.get("usrId");
            System.out.println("message 보내는 중인 interceptor에서 건너온 ID " + userId);
            
            String content = message.getPayload();
            JSONParser jp = new JSONParser();
            JSONObject jo = (JSONObject) jp.parse(content);
            String status = (String)jo.get("status");            
            if (status.equals("userrequest")) {

               List<String> userlist = new ArrayList<String>();
               Iterator usernames = users.keySet().iterator();
               
               while (usernames.hasNext()) {

                  System.out.println("userlist while문");
                  String user = (String) usernames.next();
                  System.out.println(user);
                  userlist.add(user);

               }
               jo.put("recievers",userlist);
               
				s.sendMessage(new TextMessage(jo.toJSONString()));

            } else if(status.equals("sendrequest")){

               
               Map<String, Object> mymap = session.getAttributes();
               String myId = (String) mymap.get("usrId");
                             
               //session.sendMessage(new TextMessage(myId+":"+msgarray[msgarray.length - 1]));
               List<String> userlist = (List<String>)jo.get("recievers");
               Iterator usernames = users.keySet().iterator();
               while (usernames.hasNext()) {
                  String user = (String) usernames.next();
                  for (int i = 0; i < userlist.size(); i++) {
                          if (user.equals((String)userlist.get(i))) {
                        WebSocketSession ss = users.get(user);
                               
                        if(s!=ss){
                        	
                        	jo.put("msg", myId+":"+(String)jo.get("msg"));
							ss.sendMessage(new TextMessage(jo.toJSONString()));
						
                        }
                     }
                  }
               }

            }
        }
		
		
	}catch(Exception e){
		
		e.printStackTrace();
		
	}
	}
	
}
