package ru.ventra.recruitment.ui.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Configurable
public class BundleView extends VerticalLayout implements View, Table.ColumnGenerator {
	private static final long serialVersionUID = 1L;
	
	private Table table;

	public BundleView() {
		super();
		
		setStyleName("transactions");
		setSizeFull();
		
		createToolbar();
		
		table = new Table();
		table.setSelectable(true);
		table.setMultiSelect(true);
		table.setImmediate(true);
		table.setStyleName("borderless");
		table.setSizeFull();
		
		addComponent(table);
		setExpandRatio(table, 1);
	}
	
	@PostConstruct
	protected void init() {
		
	    Object[] allBundles = new Object []{};
		
		List<Object> bundleList = new ArrayList<Object>();
	
		// Hack for adding only our relevant bundles to the list
		for (Object bundle : allBundles) {
			
			String symbolicName = bundle.getClass().toString();
			
			if (symbolicName.startsWith("ru.ventra.recruitment")) {
				bundleList.add(bundle);
			}
		}
		
		final BeanContainer<String, Object> container = new BeanContainer<String, Object>(Object.class);
		container.setBeanIdProperty("symbolicName");
		container.addAll(bundleList);
		
		table.setContainerDataSource(container);
		table.setVisibleColumns(new Object[] {"symbolicName", "version"});
		table.addGeneratedColumn("state", this);
	}

	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		
		@SuppressWarnings("unchecked")
		BeanContainer<String, Object> container = (BeanContainer<String, Object>) source.getContainerDataSource();
        
		Object bundle = container.getItem(itemId).getBean();
        
        if(columnId.equals("state")) {
        	return getStateString(bundle);
        } else {
        	return null;
        }
	}
	
	private String getStateString(Object bundle) {
        return "UNKNOWN";
	}
	
	private void createToolbar() {
		
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setStyleName("toolbar");
		toolbar.setWidth(100L, Unit.PERCENTAGE);
		toolbar.setMargin(true);
		toolbar.setSpacing(true);
		
		Label h1 = new Label("Bundles", ContentMode.HTML);
		h1.setStyleName("h1");
		h1.setSizeUndefined();
		toolbar.addComponent(h1);
		toolbar.setComponentAlignment(h1, Alignment.MIDDLE_LEFT);
		
		TextField searchField = new TextField();
		searchField.setInputPrompt("Filter");
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_LEFT);
		toolbar.setExpandRatio(searchField, 100);
		
		
		addComponent(toolbar);
	}

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
    }
}
