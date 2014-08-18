//Vsevolod Geraskin
//Final Project
package dao;

import java.util.List;

import model.ChatUser;

public interface ChatUserDao extends Dao<ChatUser,Integer> {
    public List<ChatUser> findByUsername(String username);
    public ChatUser doread(Integer id);
}
