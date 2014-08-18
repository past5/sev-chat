//Vsevolod Geraskin
//Assignment 3

package ext;


import java.util.Arrays;
import java.util.List;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import action.BaseActionBean;
import action.LoginActionBean;
import action.RegisterActionBean;
import action.SettingsActionBean;

@Intercepts(LifecycleStage.ActionBeanResolution)
public class LoginInterceptor implements Interceptor {
    @SuppressWarnings("unchecked")
    private static final List<Class<? extends BaseActionBean>> ALLOW =
        Arrays.asList(LoginActionBean.class, RegisterActionBean.class, SettingsActionBean.class);
    public Resolution intercept(ExecutionContext execContext)
        throws Exception
    {
        Resolution resolution = execContext.proceed();

        MyActionBeanContext ctx =
            (MyActionBeanContext) execContext.getActionBeanContext();

        BaseActionBean actionBean = (BaseActionBean)
            execContext.getActionBean();

        Class<? extends ActionBean> cls = actionBean.getClass();

        if (ctx.getChatUser() == null && !ALLOW.contains(cls)) {
            resolution = new RedirectResolution(LoginActionBean.class);
            if (ctx.getRequest().getMethod().equalsIgnoreCase("GET")) {
                ((RedirectResolution) resolution)
                    .addParameter("loginUrl", actionBean.getLastUrl());
            }

        }                
        return resolution;
    }
}

