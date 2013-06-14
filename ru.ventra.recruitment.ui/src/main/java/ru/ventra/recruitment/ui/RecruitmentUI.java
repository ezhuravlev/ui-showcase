package ru.ventra.recruitment.ui;

import java.util.HashMap;
import java.util.logging.Logger;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dashboard")
@Title("Ventra HRMS showcase")
public class RecruitmentUI extends UI implements Button.ClickListener {
    private static Logger log = Logger.getLogger("org.eclipse.virgo.medic.eventlog.localized");
    
	private static final long serialVersionUID = 1L;
	
	private CssLayout root;
	private LoginView loginView;
	
	private Button dashbordButton;
	private Button interviewsButton;
	private Button reportsButton;
	
	private Button selectedButton;

	private CssLayout viewContent = new CssLayout();

    private Navigator nav;

    private HashMap<String, Button> menuButtons = new HashMap<String, Button>();
	
	HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        private static final long serialVersionUID = 1L;
		{ 
        	put("/dashboard", DashboardView.class);
        	put("/interviews", CandidatesView.class); 
            put("/reports", BundleView.class);
        }
    };

	@Override
	protected void init(VaadinRequest request) {
	    
		root = new CssLayout();
		root.setStyleName("root");
		root.setSizeFull();
		
        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);
		
		showLoginView();
		
        setContent(root);
	}
	
	private void showLoginView() {

        loginView = new LoginView(this);
        
        addStyleName("login");
        root.addComponent(loginView);
    }

    public void showMainView() {
        
        removeStyleName("login");
        root.removeComponent(loginView);
        
        nav = new Navigator(this, viewContent);
        for (String route : routes.keySet()) {
            nav.addView(route, routes.get(route));
        }
        
        HorizontalLayout viewport = new HorizontalLayout();
        viewport.setStyleName("main-view");
        viewport.setSizeFull();
             
        viewContent.setStyleName("view-content");
        viewContent.setSizeFull();
        
        createSidebar(viewport);
        
        viewport.addComponent(viewContent);
        viewport.setExpandRatio(viewContent, 100);
        
        root.addComponent(viewport);

        String f = Page.getCurrent().getUriFragment();
        if (f != null && f.startsWith("!")) {
            f = f.substring(1);
        }
        if (f == null || f.equals("") || f.equals("/")) {
            nav.navigateTo("/dashboard");
            setSelectedButton(menuButtons.get("/dashboard"));
        } else {
            nav.navigateTo(f);
            setSelectedButton(menuButtons.get(f));
        }

        nav.addViewChangeListener(new ViewChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }
            
            @Override
            public void afterViewChange(ViewChangeEvent event) {
                View newView = event.getNewView();
                log.info(newView.toString());
            }
        });
    }

	private void createSidebar(Layout viewport) {
		
		VerticalLayout sidebar = new VerticalLayout();
		sidebar.addStyleName("sidebar");
		sidebar.setWidth(130L, Unit.PIXELS);
		sidebar.setHeight(100L, Unit.PERCENTAGE);
		
		CssLayout logo = new CssLayout();
		logo.setStyleName("branding");
		logo.addComponent(new Label("<span>Ventra HRMS</span>showcase", ContentMode.HTML));		
		sidebar.addComponent(logo);
		
		CssLayout menu = new CssLayout();
		menu.addStyleName("menu");
		menu.setSizeFull();
		
		dashbordButton = createButton("dashboard", "Dashboard<span class=\"badge\">2</span>", "icon-dashboard");
		menu.addComponent(dashbordButton);		
		interviewsButton = createButton("interviews", "Interviews", "icon-schedule");
		menu.addComponent(interviewsButton);		
		reportsButton = createButton("reports", "Reports", "icon-reports");
		menu.addComponent(reportsButton);
		
		sidebar.addComponent(menu);
		sidebar.setExpandRatio(menu, 1);		
		
		CssLayout user = new CssLayout();
		user.setHeight(64L, Unit.PIXELS);
		
		sidebar.addComponent(user);
		
		viewport.addComponent(sidebar);
	}
	
	private Button createButton(String id, String caption, String additionalStyleName) {
		
		NativeButton button = new NativeButton(caption, this);
		button.setHtmlContentAllowed(true);
		
		if(null != additionalStyleName) {
			button.addStyleName(additionalStyleName);
		}
		
		button.setHeight(65L, Unit.PIXELS);
		button.setWidth(100L, Unit.PERCENTAGE);
		
		button.setId(id);
		
		menuButtons.put("/" + id, button);
		
		return button;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		Button button = event.getButton();
		
        setSelectedButton(button);

        if (!nav.getState().equals("/" + button.getId())) {
            nav.navigateTo("/" + button.getId());
        }
	}
	
	private void setSelectedButton(Button button) {
		
		if(null != selectedButton) {
			
			selectedButton.removeStyleName("selected");
		}
		
		if(null != button) {
			
			button.addStyleName("selected");
			
			selectedButton = button;
		}
	}
}