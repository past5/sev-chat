//Vsevolod Geraskin
//Assignment 3

package ext;

import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBeanContext;
import dao.impl.ChatUserDaoImpl;
import model.ChatUser;
import action.chat.ChatClient;

public class MyActionBeanContext extends ActionBeanContext {
    private static final String CHATUSER = "chatuser";
    private static final String CHATCLIENT= "chatclient";
    private static final String CHATTO = "chatto";
    
    public void setChatTo(String chatto) {
    	if (chatto != null) {
    		setCurrent(CHATTO, chatto);
        }
        else {
            setCurrent(CHATTO, null);
        }	
    }
    
    public String getChatTo() {
        String chatto = getCurrent(CHATTO, null);
        return chatto;
    }
    
    public void setChatClient(ChatClient chatclient) {
    	if (chatclient != null) {
    		setCurrent(CHATCLIENT, chatclient);
        }
        else {
            setCurrent(CHATCLIENT, new ChatClient());
        }	
    }
    
    public ChatClient getChatClient() {
        ChatClient chatclient = getCurrent(CHATCLIENT, null);
        return chatclient;
    }
  
    public void setChatUser(ChatUser chatuser) {
        if (chatuser != null) {
            setCurrent(CHATUSER, chatuser.getId());
        }
        else {
            setCurrent(CHATUSER, null);
        }
    }
    public ChatUser getChatUser() {
        Integer chatuserId = getCurrent(CHATUSER, null);
        if (chatuserId == null) { return null ; }
        return new ChatUserDaoImpl().doread(chatuserId);
    }
    
    public void logout() {
        setChatUser(null);
        setChatClient(null);
        setChatTo(null);
        
        HttpSession session = getRequest().getSession();
        if (session != null) {
            session.invalidate();
        }
    }
    
    protected void setCurrent(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }

    
    @SuppressWarnings("unchecked")
    protected <T> T getCurrent(String key, T defaultValue) {
        T value = (T) getRequest().getSession().getAttribute(key);
        
        if (value == null) {
            value = defaultValue;
            setCurrent(key, value);
        }
        return value;
    }
    
}
