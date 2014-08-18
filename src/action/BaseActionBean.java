//Vsevolod Geraskin
//Final Project


package action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dao.ChatMessageDao;
import dao.ChatSettingsDao;
import dao.ChatUserDao;
import dao.impl.ChatMessageDaoImpl;
import dao.impl.ChatSettingsDaoImpl;
import dao.impl.ChatUserDaoImpl;
import model.ChatUser;
import ext.MyActionBeanContext;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;


public abstract class BaseActionBean implements ActionBean {
	private MyActionBeanContext context;

    public MyActionBeanContext getContext() {
        return context;
    }
    public void setContext(ActionBeanContext context) {
        this.context = (MyActionBeanContext) context;
    }
    @SuppressWarnings("unchecked")
    public String getLastUrl() {
        HttpServletRequest req = getContext().getRequest();
        StringBuilder sb = new StringBuilder();

        // Start with the URI and the path
        String uri = (String)
            req.getAttribute("javax.servlet.forward.request_uri");
        String path = (String)
            req.getAttribute("javax.servlet.forward.path_info");
        if (uri == null) {
            uri = req.getRequestURI(); 
            path = req.getPathInfo(); 
        }
        sb.append(uri);
        if (path != null) { sb.append(path); }

        // Now the request parameters
        sb.append('?');
        Map<String,String[]> map =
            new HashMap<String,String[]>(req.getParameterMap());
           
        
        // Append the parameters to the URL
        for (String key : map.keySet()) {
            String[] values = map.get(key);
            for (String value : values) {
                sb.append(key).append('=').append(value).append('&');
            }
        }
        // Remove the last '&'
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
    
    protected ChatUser getChatUser() {
        return getContext().getChatUser();
    }
    
    protected ChatUserDao chatuserDao = new ChatUserDaoImpl();
    protected ChatMessageDao chatmessageDao = new ChatMessageDaoImpl();
    protected ChatSettingsDao chatsettingsDao = new ChatSettingsDaoImpl();
}

