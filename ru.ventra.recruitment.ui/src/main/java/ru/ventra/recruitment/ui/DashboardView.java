package ru.ventra.recruitment.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DashboardView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	public DashboardView() {
		super();
		
		setStyleName("dashboard-view");
		setSizeFull();
		
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setStyleName("toolbar");
		toolbar.setWidth(100L, Unit.PERCENTAGE);
		toolbar.setHeight(76L, Unit.PIXELS);
		toolbar.setSpacing(true);
		addComponent(toolbar);
		
		Label h1 = new Label("My Dashboard", ContentMode.HTML);
		h1.setStyleName("h1");
		toolbar.addComponent(h1);
		toolbar.setExpandRatio(h1, 100);
		
		Button notifications = new Button("2");
		notifications.setStyleName("notifications unread icon-bell icon-only");
		toolbar.addComponent(notifications);
		
		Button iconEdit = new Button();
		iconEdit.setStyleName("icon-edit icon-only");
		toolbar.addComponent(iconEdit);
		
		HorizontalLayout dasboardRow1 = new HorizontalLayout();
		dasboardRow1.setSizeFull();
		addComponent(dasboardRow1);
		setExpandRatio(dasboardRow1, 1);
		
		HorizontalLayout dasboardRow2 = new HorizontalLayout();
		dasboardRow2.setSizeFull();
		addComponent(dasboardRow2);
		setExpandRatio(dasboardRow2, 1);
	}

	@Override
	public void enter(ViewChangeEvent e) {
		// TODO Auto-generated method stub		
	}
}
