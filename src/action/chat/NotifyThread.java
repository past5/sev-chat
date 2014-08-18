//Vsevolod Geraskin
//Final Project
package action.chat;

import java.util.ArrayList;
import java.util.List;

import model.ChatMessage;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangObject;

public class NotifyThread extends Thread {
	private ChatNotify notify;
	private ChatNotifyList notifylist;
	private OtpConnection conn;
	private ChatMessage broadcastmessage;
	private String name;
	
	private static final String STATUS = "status";	
			
	private boolean run = true;
	
	public OtpErlangObject received;
	
	NotifyThread(String name, ChatNotify notify, ChatNotifyList notifylist, OtpConnection conn) {
		this.name = name;
        this.notify = notify;
        this.notifylist = notifylist;
        this.conn = conn;
    }
	
    public void run() {
    	try {
			while (run) {
				received = conn.receiveRPC(); 
				broadcastmessage = new ChatMessage();
				
				java.util.Date date = new java.util.Date(System.currentTimeMillis());
		    	java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		    	broadcastmessage.setMessagetime(timestamp);
		    	
		    	System.out.println("listen thread received: " + received);
				//server confirmation messages
				if(received.toString().substring(0, 2).equalsIgnoreCase("ok")) {
					if(received.toString().equalsIgnoreCase("oksubscribe")) {
						  broadcastmessage = new ChatMessage();
						  date = new java.util.Date(System.currentTimeMillis());
					      timestamp = new java.sql.Timestamp(date.getTime());
					      broadcastmessage.setMessagetime(timestamp);
						  broadcastmessage.setUsernamefrom(STATUS);
						  broadcastmessage.setChatmessage(name + " joins the chat");
						  
						  notify.notify(broadcastmessage);
					} else if(received.toString().equalsIgnoreCase("okunsubscribe")) {
						  broadcastmessage = new ChatMessage();
						  date = new java.util.Date(System.currentTimeMillis());
					      timestamp = new java.sql.Timestamp(date.getTime());
					      broadcastmessage.setMessagetime(timestamp);
						  broadcastmessage.setUsernamefrom(STATUS);
						  broadcastmessage.setChatmessage(name + " leaves the chat");
						  notify.notify(broadcastmessage);
						  
						  System.out.println("listen thread closing...");
						  run = false;
					}
				//client list
				} else if(received.toString().substring(0, 2).equalsIgnoreCase("{[")) {
					List<String> ClientList = new ArrayList<String>();
							
					String[] splitreceived = received.toString().split(",\"");
					
					for (int i=1; i<splitreceived.length-1; i++) {	
						ClientList.add(splitreceived[i].substring(0,
								splitreceived[i].indexOf("\"")));
		
					}
					notifylist.notify(ClientList);
	
				//chat message
				} else if(received.toString().substring(0, 1).equalsIgnoreCase("{")) {
					String[] splitreceived = received.toString().split("\",");
					
					broadcastmessage.setChatmessage(splitreceived[0].substring(2));
					broadcastmessage.setUsernamefrom(splitreceived[1].substring(2));
					
					//private message
					if (!splitreceived[2].substring(0,2).equalsIgnoreCase("[]")) {
						broadcastmessage.setUsernameto(splitreceived[2].replace("\"","").replace("}",""));
					}
					
					if (broadcastmessage.getUsernamefrom().equalsIgnoreCase(name)) {
						notify.notify(broadcastmessage);
					} else {
						notify.notifynowrite(broadcastmessage);
					}
				}   	
			} 
    	} catch (Exception e) {
    		broadcastmessage = new ChatMessage();
    		
    		java.util.Date date = new java.util.Date(System.currentTimeMillis());
	    	java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
	    	broadcastmessage.setMessagetime(timestamp);
	    	
			broadcastmessage.setUsernamefrom(STATUS);
			broadcastmessage.setUsernameto(name);
			broadcastmessage.setChatmessage("notify thread error is: " + e.toString());
			  
			notify.notify(broadcastmessage);
    	}
    }
}
