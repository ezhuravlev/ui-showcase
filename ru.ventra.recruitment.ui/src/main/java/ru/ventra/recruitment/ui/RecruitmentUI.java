package ru.ventra.recruitment.ui;

import java.util.HashMap;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dashboard")
@Title("Ventra HRMS UI Showcase")
public class RecruitmentUI extends UI implements Button.ClickListener {
	private static final long serialVersionUID = 1L;
	
	private Button dashbordButton;
	private Button interviewsButton;
	private Button reportsButton;
	
	private Button selectedButton;

    //CssLayout root = new CssLayout();

    VerticalLayout loginLayout;

    //CssLayout menu = new CssLayout();

	private CssLayout viewContent;

    private Navigator nav;
	
	HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        private static final long serialVersionUID = 1L;
		{ 
        	put("/dashboard", DashboardView.class);
        	put("/sales", CandidatesView.class); 
        }
    };

	@Override
	protected void init(VaadinRequest request) {
		
		CssLayout root = new CssLayout();
		root.setStyleName("root");
		root.setSizeFull();
		
		HorizontalLayout viewport = new HorizontalLayout();
		viewport.setStyleName("main-view");
		viewport.setSizeFull();
		
		viewContent = new CssLayout();		
		viewContent.setStyleName("view-content");
		viewContent.setSizeFull();
		
		createSidebar(viewport);
		
		viewport.addComponent(viewContent);
		viewport.setExpandRatio(viewContent, 100);
		
		root.addComponent(viewport);
		
        setContent(root);
	}
	
	private void buildMainView() {
        nav = new Navigator(this, viewContent);
        for (String route : routes.keySet()) {
            nav.addView(route, routes.get(route));
        }
        
	}

	private void createSidebar(Layout viewport) {
		
		VerticalLayout sidebar = new VerticalLayout();
		sidebar.addStyleName("sidebar");
		sidebar.setWidth(130L, Unit.PIXELS);
		sidebar.setHeight(100L, Unit.PERCENTAGE);
		
		CssLayout logo = new CssLayout();
		logo.setStyleName("branding");
		logo.addComponent(new Label("<span>Ventra HRMS</span>UI Showcase", ContentMode.HTML));		
		sidebar.addComponent(logo);
		
		CssLayout menu = new CssLayout();
		menu.addStyleName("menu");
		menu.setSizeFull();
		
		dashbordButton = createButton("Dashboard<span class=\"badge\">2</span>", "icon-dashboard");
		menu.addComponent(dashbordButton);
		
		interviewsButton = createButton("Interviews", "icon-schedule");
		menu.addComponent(interviewsButton);
		
		// FIXME
		setSelectedButton(interviewsButton);
		showView(new CandidatesView());
		
		reportsButton = createButton("Reports", "icon-reports");
		menu.addComponent(reportsButton);
		
		sidebar.addComponent(menu);
		sidebar.setExpandRatio(menu, 1);
		
		
		CssLayout user = new CssLayout();
		user.setHeight(64L, Unit.PIXELS);
		
		sidebar.addComponent(user);
		
		viewport.addComponent(sidebar);
	}
	
	private Button createButton(String caption, String additionalStyleName) {
		
		NativeButton button = new NativeButton(caption, this);
		button.setHtmlContentAllowed(true);
		
		if(null != additionalStyleName) {
			button.addStyleName(additionalStyleName);
		}
		
		button.setHeight(65L, Unit.PIXELS);
		button.setWidth(100L, Unit.PERCENTAGE);
		
		return button;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		setSelectedButton(event.getButton());
		
		if(event.getButton() == dashbordButton) {
			showView(new DashboardView());
		} else if(event.getButton() == interviewsButton) {
			showView(new CandidatesView());
		} else if(event.getButton() == reportsButton) {
			showView(new BundleView());
		}
	}
	
	private void showView(Component view) {
		
		if(viewContent.getComponentCount() > 0) {
			viewContent.removeAllComponents();			
		}
		
		/*if(view instanceof DashboardView) {
			
		}*/
		
		viewContent.addComponent(view);
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