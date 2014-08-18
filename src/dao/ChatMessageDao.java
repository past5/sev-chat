//Vsevolod Geraskin
//Final Project
package dao;

import java.util.List;

import model.ChatMessage;

public interface ChatMessageDao extends Dao<ChatMessage,Integer> {
	public List<ChatMessage> getmessages(String Name);
}
