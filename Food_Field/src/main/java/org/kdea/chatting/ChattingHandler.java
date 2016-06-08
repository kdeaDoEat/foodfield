package org.kdea.chatting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChattingHandler extends TextWebSocketHandler {

    public static Map<String, WebSocketSession> users = new ConcurrentHashMap();
	
    @Autowired
    private ChattingService chatsvc;
        
	/*session에서 id 가져오기 전*/
	public static int playerCnt=0;
	/*session에서 id 가져오기 전*/
    
    // 웹소켓 서버측에 텍스트 메시지가 접수되면 호출되는 메소드
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log("WebSocketSession ID : " + session.getId() + "메세지 문자 길이:" + message.getPayloadLength());
        // textmessage

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
              s.sendMessage(new TextMessage(newsend));

           } else {

              String[] msgarray = message.getPayload().split("/");
              for (int i = 0; i < msgarray.length; i++) {

                 System.out.println("메세지 분리 합니다~" + i + msgarray[i]);

              }
              
              Map<String, Object> mymap = session.getAttributes();
              String myId = (String) mymap.get("usrId");
                            
              session.sendMessage(new TextMessage(myId+":"+msgarray[msgarray.length - 1]));
              List<String> userlist = new ArrayList<String>();
              Iterator usernames = users.keySet().iterator();
              while (usernames.hasNext()) {
                 String user = (String) usernames.next();
                 for (int i = 0; i < msgarray.length - 1; i++) {
                         if (user.equals(msgarray[i])) {
                       WebSocketSession ss = users.get(user);
                       if(s!=ss){
                       ss.sendMessage(new TextMessage(myId+":"+msgarray[msgarray.length - 1]));
                       }
                    }
                 }
              }

           }
           log(userId + "가 보낸 메세지 " + message.getPayload());
        }
    }
 
    // 웹소켓 서버에 클라이언트가 접속하면 호출되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
         
        log("WebSocketSession ID : " + session.getId() + "현재 웹 소켓 세션 ID");

        Map<String, Object> map = session.getAttributes();
        String userId = (String) map.get("usrId");
        System.out.println("afterconn interceptor에서 건너온 ID " + userId);
        
        //session에서 id가져오기 전
        playerCnt++;
        
        users.put(userId+playerCnt, session);
        
    }
 
    // 클라이언트가 접속을 종료하면 호출되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log("WebSocketSession ID : " + session.getId() + "현재 웹 소켓 세션 ID");
        Map<String, Object> map = session.getAttributes();
        String userId = (String) map.get("usrId");
        System.out.println("afterclose interceptor에서 건너온 ID " + userId);

        //session에서 id가져오기 전
        
        users.remove(userId+playerCnt);
        System.out.println("클라이언트 접속해제");
    }
 
    // 메시지 전송시나 접속해제시 오류가 발생할 때 호출되는 메소드
    @Override
    public void handleTransportError(WebSocketSession session,
            Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        System.out.println("전송오류 발생");
    }
    
    private void log(String logmsg) {
        System.out.println(new Date() + " : " + logmsg);
     }
}
