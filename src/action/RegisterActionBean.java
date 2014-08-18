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

import model.ChatUser;
import nonext.PasswordTypeConverter;

@HttpCache(allow=false)
@Wizard(startEvents={"view","done"})
public class RegisterActionBean extends BaseActionBean
    implements ValidationErrorHandler
{
    private static final String VIEW = "/jsp/register.jsp";
    private static final String DONE = "/jsp/reg_complete.jsp";

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    public Resolution register() {
    	ChatUser chatuser = getChatuser();
        chatuserDao.save(chatuser);
        chatuserDao.commit();
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
        @Validate(field="firstName",  minlength=2, maxlength=10, required=true),
        @Validate(field="lastName",  minlength=2, maxlength=10, required=true),
        @Validate(field="username", minlength=2, maxlength=10, required=true),
        @Validate(field="password", minlength=2, maxlength=10, required=true,
            converter=PasswordTypeConverter.class)
    })
    private ChatUser chatuser;
    public ChatUser getChatuser() {
        return chatuser;
    }
    public void setChatuser(ChatUser chatuser) {
        this.chatuser = chatuser;
    }

    @Validate(required=true, converter=PasswordTypeConverter.class)
    private String confirmPassword;
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @ValidationMethod
    public void validateUsernameAndPasswords(ValidationErrors errors){
    	ChatUser chatuser = getChatuser();
        String username = chatuser.getUsername();
        if (chatuserDao.findByUsername(username).size() > 0) {
            errors.addGlobalError(
              new SimpleError("Username " + username + " already taken"));
        }
        if (!chatuser.getPassword().equals(confirmPassword)) {
            errors.addGlobalError(
                new SimpleError("Passwords do not match"));
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