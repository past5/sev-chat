//Vsevolod Geraskin
//Final Project
package action;

import java.util.List;

import org.stripesstuff.stripersist.Stripersist;

import action.chat.ChatNotify;

import model.ChatMessage;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;

@HttpCache(allow=false)
public class NotifyActionBean extends ChatActionBean implements ChatNotify {
	private static final String NOTIFY = "/jsp/notify.jsp";

	@DefaultHandler
    @DontValidate
    public Resolution view() {
		getContext().getChatClient().setNotify(this);

		return new ForwardResolution(NOTIFY);
    }

	public List<ChatMessage> getMessages() {
		return chatmessageDao.getmessages(getUsername());
	}
	
	public synchronized void notify(ChatMessage message) {
		try {
			Stripersist.requestInit();
	
			chatmessageDao.save(message);
			chatmessageDao.commit();
    	} catch (Exception e){
    		e.printStackTrace();
    	} finally {
    		Stripersist.requestComplete();
    	}
	}
	
	public synchronized void notifynowrite(ChatMessage message) {
		//only senders write to db
	}
}
