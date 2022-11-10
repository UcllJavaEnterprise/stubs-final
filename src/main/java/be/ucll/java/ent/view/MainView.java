package be.ucll.java.ent.view;

import be.ucll.java.ent.controller.UserController;
import be.ucll.java.ent.utils.BeanUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Route("main")
@PageTitle("StuBS")
@CssImport("styles/main-view.css")
class MainView extends AppLayout implements BeforeEnterObserver {
    private Logger logger = LoggerFactory.getLogger(MainView.class);

    @Autowired
    private UserController userCtrl;

    // Content views
    private StudentenView sView;
    private LeermodulesView lmView;

    @Autowired
    private MessageSource msgSrc;
    private Locale loc;

    // Left navigation tabs
    private Tab tab1;
    private static final String TABNAME1 = "Studenten";
    private Tab tab2;
    private static final String TABNAME2 = "Leermodules";
    private Tab tab3;
    private Tab tab4;
    private Tabs tabs;

    private Button btnLogout;

    public MainView() {
        msgSrc = BeanUtil.getBean(MessageSource.class);

        // Locale derived from the Browser language settings
        loc = VaadinSession.getCurrent().getLocale();

        // Header / Menu bar on the top of the page
        Image img = new Image("img/ucll.jpg", "ucll-logo");
        img.setHeight("30px");
        H3 header = new H3(msgSrc.getMessage("app.title", null, loc));
        header.setId("header-layout");

        btnLogout = new Button("Log uit");
        btnLogout.addClickListener(e -> {
            handleLogoutClicked(e);
        });
        btnLogout.setId("aligneer-rechts");

        addToNavbar(new DrawerToggle(),
                img,
                new Html("<span>&nbsp;&nbsp;</span>"),
                header,
                new Html("<span>&nbsp;-&nbsp;</span>"),
                new Icon(VaadinIcon.ACADEMY_CAP),
                btnLogout);

        // Tabs on the left side drawer
        tab1 = new Tab(TABNAME1);
        tab2 = new Tab(TABNAME2);
        tab3 = new Tab("Help");
        tab3.setEnabled(false);
        tab4 = new Tab(new Icon(VaadinIcon.COG));

        tabs = new Tabs(tab1, tab2, tab3, tab4);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            handleTabClicked(event);
        });
        addToDrawer(tabs);
    }

    @PostConstruct
    private void setMainViewContent() {
        sView = new StudentenView();
        sView.loadData();

        lmView = new LeermodulesView();
        lmView.loadData();

        // As default load the studentenview
        setContent(sView);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Show tab4 only for admin users.
        tab4.setVisible(false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            tab4.setVisible(true);
        }
    }

    private void handleTabClicked(Tabs.SelectedChangeEvent event) {
        Tab selTab = tabs.getSelectedTab();
        if (selTab.getLabel() != null) {
            if (selTab.getLabel().equals(TABNAME1)) {
                setContent(sView);
            } else if (selTab.getLabel().equals(TABNAME2)) {
                setContent(lmView);
            } else {
                setContent(new H2("Te implementeren scherm voor Admins only"));
            }
        }
    }

    private void handleLogoutClicked(ClickEvent event) {
        getUI().ifPresent(ui -> ui.navigate("login"));

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
