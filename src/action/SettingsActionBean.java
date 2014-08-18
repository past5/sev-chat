//Vsevolod Geraskin
//Final Project
package action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.Wizard;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import model.ChatSettings;
import nonext.PasswordTypeConverter;

@HttpCache(allow=false)
@Wizard(startEvents={"view","done"})
public class SettingsActionBean extends BaseActionBean
    implements ValidationErrorHandler
{
    private static final String VIEW = "/jsp/settings.jsp";
    private static final String DONE = "/jsp/set_complete.jsp";
        
    private int sid;
    public int getSid() {
        return sid;
    }
    
    public void setSid(int sid) {
        this.sid = sid;
    }
    
    public ChatSettings getChatsettings() {
    	ChatSettings settings = chatsettingsDao.doread(sid);
        return settings;
    }
    
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    public Resolution save() {
    	ChatSettings chatsettings = getChatsettings();
    	chatsettings.setPassword(chatsettingsDao.doread(sid).getPassword());
        chatsettingsDao.merge(chatsettings);
        chatsettingsDao.commit();
        return new RedirectResolution(getClass(), "done");
    }
    @DontValidate
    public Resolution done() {
        return new ForwardResolution(DONE);
    }
    @DontValidate
    public Resolution cancel() {
        return new RedirectResolution(LoginActionBean.class);
    }
    @ValidateNestedProperties({
        @Validate(field="serverip",  maxlength=15, required=true),
        @Validate(field="servername", maxlength=10, required=true),
        @Validate(field="servercookie", maxlength=10, required=true)
    })

    @Validate(required=true, converter=PasswordTypeConverter.class)
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @ValidationMethod
    public void validateUsernameAndPasswords(ValidationErrors errors){
    	String password = getPassword();
 
        if (!password.equals(chatsettingsDao.doread(1).getPassword())) {
            errors.addGlobalError(
                new SimpleError("Administrator password incorrect"));
        }
    }
    
    public Resolution handleValidationErrors(ValidationErrors errors){
        if (errors.hasFieldErrors()) {
            errors.addGlobalError(
                new SimpleError("All fields required"));
        }
        return null;
    }
}