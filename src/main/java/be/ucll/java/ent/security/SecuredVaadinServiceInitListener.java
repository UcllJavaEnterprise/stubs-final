package be.ucll.java.ent.security;

import be.ucll.java.ent.view.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
// Wire Spring Security to the Vaadin navigation/routing system.
public class SecuredVaadinServiceInitListener implements VaadinServiceInitListener {
    private static final Logger log = Logger.getLogger(SecuredVaadinServiceInitListener.class.getName());

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    /*
      Except for the Login and the Register view, ALL other Vaadin interaction can be done by AUTHENTICATED users only
     */
    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }
}
