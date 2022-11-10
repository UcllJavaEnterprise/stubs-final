package be.ucll.java.ent.view;

import be.ucll.java.ent.utils.BeanUtil;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Route("")
@RouteAlias("login")
@PageTitle("StuBS - Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private Logger logger = LoggerFactory.getLogger(LoginView.class);

    private LoginI18n i18n;
    private LoginForm frmLogin;

    @Autowired
    private MessageSource msgSrc;
    private Locale loc;

    public LoginView() {
        msgSrc = BeanUtil.getBean(MessageSource.class);

        // Locale derived from the Browser language settings
        loc = VaadinSession.getCurrent().getLocale();
        // For testing purposes:
        // loc = new Locale("fr");
        logger.debug("Browser/Session locale: " + loc.toString());

        i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle(msgSrc.getMessage("app.title", null, loc));
        i18n.getForm().setUsername(msgSrc.getMessage("login.lbl.userid", null, loc));
        i18n.getForm().setPassword(msgSrc.getMessage("login.lbl.password", null, loc));
        i18n.getErrorMessage().setTitle(msgSrc.getMessage("login.failure.tit", null, loc));
        i18n.getErrorMessage().setMessage(msgSrc.getMessage("login.failure.msg", null, loc));
        // i18n.setAdditionalInformation("By using this application you consent to ...");

        frmLogin = new LoginForm(i18n);
        frmLogin.setAction("login");
        frmLogin.setForgotPasswordButtonVisible(false);

        add(frmLogin);
        this.setAlignItems(Alignment.CENTER);
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            frmLogin.setError(true);
        }
    }

}