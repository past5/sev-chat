//Vsevolod Geraskin
//Final Project

package action.chat;

import model.ChatMessage;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangString;

public class ChatClient {
	private static final String STATUS = "status";	
	
	private OtpConnection conn;
	private OtpSelf self;
	private OtpPeer other;
	
	private ChatNotify notify;
	private ChatNotifyList notifylist;
	private String name;
	private Thread t;
	
	public OtpErlangObject received;
	private String peer;
	private String cookie;
	private String ip;
	
	private boolean connected;
	
	private ChatMessage broadcastmessage;

	public ChatClient() {
		connected = false;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void setNotifyList(ChatNotifyList _notifylist) {
		 this.notifylist = _notifylist; 
	 }
	
	 public void setNotify(ChatNotify _notify) {
		 this.notify = _notify; 
	 }
	 
	 public void setName(String _name) {
		 this.name = _name; 
	 }
	 
	 public void setPeer(String _peer) {
		 this.peer = _peer;
	 }
	 
	 public void setCookie(String _cookie) {
		 this.cookie = _cookie; 
	 }
	 
	 public void setIp(String _ip) {
		 this.ip = _ip;
	 }
	 
	  public void broadcast(String what) {
		  OtpErlangObject[] elements;
		  OtpErlangList erlangList;
		  
		  elements = new OtpErlangObject[] {new OtpErlangString(what), new OtpErlangString(name)};
		  erlangList = new OtpErlangList(elements);	
		  
		  try {
			  conn.sendRPC("sevchat_server","broadcast",erlangList);
		  } catch (Exception exp) {
			  broadcastmessage = new ChatMessage();
			  
			  java.util.Date date = new java.util.Date(System.currentTimeMillis());
		      java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		      broadcastmessage.setMessagetime(timestamp);
			  broadcastmessage.setUsernamefrom(STATUS);
			  broadcastmessage.setUsernameto(name);
			  broadcastmessage.setChatmessage("broadcast error is: " + exp.toString());
			  
			  notify.notify(broadcastmessage);
		  }
	  }
	  
	  public void whisper(String what, String who) {
		  OtpErlangObject[] elements;
		  OtpErlangList erlangList;
		  
		  elements = new OtpErlangObject[] {new OtpErlangString(what), new OtpErlangString(name), new OtpErlangString(who)};
		  erlangList = new OtpErlangList(elements);	
		  
		  try {
			  conn.sendRPC("sevchat_server","whisper",erlangList);
		  } catch (Exception exp) {
			  broadcastmessage = new ChatMessage();
			  
			  java.util.Date date = new java.util.Date(System.currentTimeMillis());
		      java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		      broadcastmessage.setMessagetime(timestamp);
			  broadcastmessage.setUsernamefrom(STATUS);
			  broadcastmessage.setUsernameto(name);
			  broadcastmessage.setChatmessage("whisper error is: " + exp.toString());
			  
			  notify.notify(broadcastmessage);
		  }
	  }
	  
	  public void clientlist() {  
		  OtpErlangObject[] elements;
		  OtpErlangList erlangList;
		  
		  elements = new OtpErlangObject[] {new OtpErlangString(name)};
		  erlangList = new OtpErlangList(elements);	
		  
		  try {
			  conn.sendRPC("sevchat_server","clientlist",erlangList);
		  } catch (Exception exp) {
			  broadcastmessage = new ChatMessage();
			  
			  java.util.Date date = new java.util.Date(System.currentTimeMillis());
		      java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		      broadcastmessage.setMessagetime(timestamp);
			  broadcastmessage.setUsernamefrom(STATUS);
			  broadcastmessage.setUsernameto(name);
			  broadcastmessage.setChatmessage("clientlist error is: " + exp.toString());
			  
			  notify.notify(broadcastmessage);
		  }
	  }
	  
	  public void connect() {
		  OtpErlangObject[] elements;
		  OtpErlangList erlangList;
		  
		  connected = true;
		  
		  broadcastmessage = new ChatMessage();
		  java.util.Date date = new java.util.Date(System.currentTimeMillis());
	      java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
	      broadcastmessage.setMessagetime(timestamp);
		  broadcastmessage.setUsernamefrom(STATUS);
		  broadcastmessage.setUsernameto(name);
		  broadcastmessage.setChatmessage("please wait, connecting to " + peer + "@" + ip  + "...");
		  
		  notify.notify(broadcastmessage);

		  String javaClient = name.trim() + "@" + ip.trim();
		  
		  try {
			  self = new OtpSelf(javaClient, cookie.trim());
			  other = new OtpPeer(peer.trim() + "@" + ip.trim());
			  conn = self.connect(other);
			  
			  broadcastmessage = new ChatMessage();
			  date = new java.util.Date(System.currentTimeMillis());
		      timestamp = new java.sql.Timestamp(date.getTime());
		      broadcastmessage.setMessagetime(timestamp);
			  broadcastmessage.setUsernamefrom(STATUS);
			  broadcastmessage.setUsernameto(name);
			  broadcastmessage.setChatmessage("connection established with " + peer + "@" + ip);
			  
			  notify.notify(broadcastmessage);
			  
			  elements = new OtpErlangObject[] {self.pid(),new OtpErlangString(name)};
			  erlangList = new OtpErlangList(elements);	
			  
			  t = new NotifyThread(name, notify, notifylist, conn);
	    	  t.start();
	    	  
			  conn.sendRPC("sevchat_server","subscribe",erlangList);

		  } catch (Exception exp) {
			  broadcastmessage = new ChatMessage();
			  date = new java.util.Date(System.currentTimeMillis());
		      timestamp = new java.sql.Timestamp(date.getTime());
		      broadcastmessage.setMessagetime(timestamp);
			  broadcastmessage.setUsernamefrom(STATUS);
			  broadcastmessage.setUsernameto(name);
			  broadcastmessage.setChatmessage("connect error is: " + exp.toString());
			  
			  connected = false;
			  
			  notify.notify(broadcastmessage);
		  }
	  }

	 public void disconnect() {
		 	OtpErlangObject[] elements;
		  	OtpErlangList erlangList;
		  	
		  	connected = false;
		  	
		  	try {
			 	elements = new OtpErlangObject[] {self.pid(),new OtpErlangString(name)};
				erlangList = new OtpErlangList(elements);	
				
				conn.sendRPC("sevchat_server","unsubscribe",erlangList);
				
				broadcastmessage = new ChatMessage();
				java.util.Date date = new java.util.Date(System.currentTimeMillis());
			    java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			    broadcastmessage.setMessagetime(timestamp);
				broadcastmessage.setUsernamefrom(STATUS);
				broadcastmessage.setUsernameto(name);
				broadcastmessage.setChatmessage("disconnecting...");
				  
				notify.notify(broadcastmessage);
				
			 	if(conn != null){
			 		conn.close();
			 	}
			 	
			 	broadcastmessage = new ChatMessage();
			 	date = new java.util.Date(System.currentTimeMillis());
			    timestamp = new java.sql.Timestamp(date.getTime());
			    broadcastmessage.setMessagetime(timestamp);
				broadcastmessage.setUsernamefrom(STATUS);
				broadcastmessage.setUsernameto(name);
				broadcastmessage.setChatmessage("successfuly disconnected");
				
				notify.notify(broadcastmessage);
		  	} catch (Exception exp) {
		  		broadcastmessage = new ChatMessage();
		  		java.util.Date date = new java.util.Date(System.currentTimeMillis());
			    java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			    broadcastmessage.setMessagetime(timestamp);
				broadcastmessage.setUsernamefrom(STATUS);
				broadcastmessage.setUsernameto(name);
				broadcastmessage.setChatmessage("disconnect error is: " + exp.toString());
				  
				notify.notify(broadcastmessage);
			}
	 }

}
