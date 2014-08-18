//Vsevolod Geraskin
//Final Project
package dao.impl;

import java.util.List;

import dao.ChatUserDao;
import model.ChatUser;

public class ChatUserDaoImpl extends BaseDaoImpl<ChatUser,Integer> implements ChatUserDao
{
    public List<ChatUser> findByUsername(String username) {
        return findBy("username", username);
    }
    
    public ChatUser doread(Integer id) {
		 return read(id);
	 }
}
