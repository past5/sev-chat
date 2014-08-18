//Vsevolod Geraskin
//Final Project
package action;

import java.util.ArrayList;
import java.util.List;

import org.stripesstuff.stripersist.Stripersist;

import action.chat.ChatClient;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import model.ChatUser;
import nonext.PasswordTypeConverter;

@HttpCache(allow=false)

public class LoginActionBean extends BaseActionBean {
    private static final String VIEW = "/jsp/login.jsp";
    private ChatUser chatuser;
    private static boolean init = false;
    
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }
    
    public Resolution login() {
    	if (!init) {
    		new Stripersist().init(getClass().getResource("/META-INF/persistence.xml"));
    		init = true;
    	}
    	
        getContext().setChatUser(chatuser);
        getContext().setChatClient(new ChatClient());
        getContext().setChatTo(null);
        
        if (loginUrl != null) {
            return new RedirectResolution(loginUrl);
        }
        return new RedirectResolution(ChatActionBean.class);
    }
    public String loginUrl;
    
    @Validate(required=true)
    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Validate(required=true, converter=PasswordTypeConverter.class)
    private String password;
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    @ValidationMethod
    public void validateLogin(ValidationErrors errors) {
    	List<ChatUser> loginlist  =new ArrayList<ChatUser>();
    	loginlist = chatuserDao.findByUsername(username);
    	
    	if (loginlist.size() == 0) {
    		errors.add("username",
	                new SimpleError("username not found"));
    	} else { 
    		chatuser = chatuserDao.findByUsername(username).get(0);
	        if (!chatuser.getUsername().equals(username)) {
	            errors.add("username",
	                new SimpleError("username not found"));
	        }
	        else if (!chatuser.getPassword().equals(password)) {
	            errors.add("password",
	                new SimpleError("password incorrect"));
	        }
    	}
    }
}
