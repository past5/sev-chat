//Vsevolod Geraskin
//Final Project

package action;


import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

@HttpCache(allow=false)
public class LogoutActionBean extends BaseActionBean {
    public Resolution logout() {
    	if (getContext().getChatClient().isConnected()) getContext().getChatClient().disconnect();
        getContext().logout();
        return new RedirectResolution(LoginActionBean.class);
    }
}

