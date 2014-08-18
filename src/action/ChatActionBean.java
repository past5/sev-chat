//Vsevolod Geraskin
//Final Project
package action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

@HttpCache(allow=false)

public class ChatActionBean extends BaseActionBean implements ValidationErrorHandler {
	private static final String VIEW = "/jsp/chat.jsp";
	private static final String RESULT = "/jsp/result.jsp";
	private static final String ERRORS = "/jsp/errors.jsp";
	
	private String result;
	
	@DefaultHandler
    @DontValidate
    public Resolution view() {
		getContext().getChatClient().setName(getUsername());
		getContext().getChatClient().setIp(chatsettingsDao.doread(1).getServerip());
		getContext().getChatClient().setPeer(chatsettingsDao.doread(1).getServername());
		getContext().getChatClient().setCookie(chatsettingsDao.doread(1).getServercookie());
	
		return new ForwardResolution(VIEW);
    }
	
    public String getUsername() {
    	return chatuserDao.doread(getContext().getChatUser().getId()).getUsername();
    }
    
    public boolean getConnected() {
    	return getContext().getChatClient().isConnected();
    }
    
    public Resolution handleValidationErrors(ValidationErrors errors) {
    	return new ForwardResolution(ERRORS);
    }
    
	public Resolution connect() {
		if (!getContext().getChatClient().isConnected()) getContext().getChatClient().connect();
		result = "connect button clicked";
		return new ForwardResolution(RESULT);
	}
	
	public Resolution disconnect() {
		if (getContext().getChatClient().isConnected()) getContext().getChatClient().disconnect();
		result = "disconnect button clicked";
		return new ForwardResolution(RESULT);
	}
	
	public String getResult() {
		return result;
	}
	
	@Validate(maxlength=256)
	private String chatLine;
	public String getChatLine() {
		return chatLine;
	}
	
	public void setChatLine(String chatLine) {
		this.chatLine = chatLine;
	}
	
	public String getChatto() {
		return getContext().getChatTo();
	}
	
	public Resolution cancel() {
		getContext().setChatTo(null);
		return new ForwardResolution(VIEW);
	}
	
	public Resolution broadcast() {
		result = "you say nothing";
		
		if (chatLine != null) {
			if (getChatto() == null) {
				if (getContext().getChatClient().isConnected()) getContext().getChatClient().broadcast(chatLine);
				result = "you say " + chatLine;
			} else {
				if (getContext().getChatClient().isConnected()) getContext().getChatClient().whisper(chatLine,getChatto());
				result = "you whisper " + chatLine + " to " + getChatto();
			}
		}
		return new ForwardResolution(RESULT);
	}
}

