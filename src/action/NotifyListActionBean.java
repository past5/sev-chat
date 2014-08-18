//Vsevolod Geraskin
//Final Project
package action;

import java.util.ArrayList;
import java.util.List;

import action.chat.ChatNotifyList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;

@HttpCache(allow=false)
public class NotifyListActionBean extends ChatActionBean implements ChatNotifyList {
	private static final String NOTIFY = "/jsp/notifylist.jsp";
	private static final String CHAT = "/jsp/chat.jsp";
	
	private static List<String> Clients = new ArrayList<String>();
	
	@DefaultHandler
    @DontValidate
    public Resolution view() {
		getContext().getChatClient().setNotifyList(this);
		
		return new ForwardResolution(NOTIFY);
    }

	private String toclient;
    public String getToclient() {
        return toclient;
    }
    
    public void setToclient(String toclient) {
        this.toclient = toclient;
    }
    
    public Resolution whisper() {
    	getContext().setChatTo(getToclient());
    	return new ForwardResolution(CHAT);
    }
    
	public List<String> getClients() {
		return Clients;
	}
	
	public synchronized void notify(List<String> ClientList) {	
		Clients = ClientList;
	}
}
