//Vsevolod Geraskin
//Final Project
package dao.impl;

import java.util.List;

import dao.ChatMessageDao;
import model.ChatMessage;

public class ChatMessageDaoImpl extends BaseDaoImpl<ChatMessage,Integer> implements ChatMessageDao {
	public List<ChatMessage> getmessages(String Name) {
		 return findBy("usernamefrom","usernameto",Name);
	 }
}
