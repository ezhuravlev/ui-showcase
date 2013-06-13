package ru.ventra.recruitment.ui;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ru.ventra.recruitment.domain.Candidate;
import ru.ventra.recruitment.service.UserService;
import ru.ventra.recruitment.ui.Dialog.ResponseEvent;
import ru.ventra.recruitment.ui.jpacontainer.TransactionalEntityProvider;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@Configurable
public class CandidatesView extends VerticalLayout implements View, ClickListener, ValueChangeListener, ItemClickListener {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger("org.eclipse.virgo.medic.eventlog.localized");

	@Autowired
	private UserService userService;
	private JPAContainer<Candidate> candidates;
	private EntityManager em;
	
	private Table table;
	private Button createButton;
	private Button deleteButton;
	private Button importButton;



	private Set<Object> selectedIds = new HashSet<Object>();
	
	public CandidatesView() {
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
		table.addItemClickListener(this);
		table.addValueChangeListener(this);		
		table.setColumnFooter("id", "Total");
		table.setColumnFooter("middleName", String.valueOf(1000));
		table.setFooterVisible(true);
		
		addComponent(table);
		setExpandRatio(table, 1);
	}
	
	@PostConstruct
	protected void init() {
		
		em = userService.getEntityManager();
		
		candidates = new JPAContainer<Candidate>(Candidate.class);
		candidates.setEntityProvider(new TransactionalEntityProvider<Candidate>(Candidate.class, em));
		candidates.setWriteThrough(false);
		//TODO: itemSetChangeEventListener-aware
		
		table.setContainerDataSource(candidates);
		table.setVisibleColumns(new Object[] {"id", "firstName", "lastName", "middleName"});
	}
	
	private void createToolbar() {
		
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setStyleName("toolbar");
		toolbar.setWidth(100L, Unit.PERCENTAGE);
		toolbar.setMargin(true);
		toolbar.setSpacing(true);
		
		Label h1 = new Label("Candidates", ContentMode.HTML);
		h1.setStyleName("h1");
		h1.setSizeUndefined();
		toolbar.addComponent(h1);
		toolbar.setComponentAlignment(h1, Alignment.MIDDLE_LEFT);
		
		TextField searchField = new TextField();
		searchField.setInputPrompt("Filter");
		toolbar.addComponent(searchField);
		toolbar.setComponentAlignment(searchField, Alignment.MIDDLE_LEFT);
		toolbar.setExpandRatio(searchField, 100);
		
		importButton = new Button("Import CV", this);
		importButton.setStyleName("small");
		toolbar.addComponent(importButton);
		toolbar.setComponentAlignment(importButton, Alignment.MIDDLE_LEFT);
		
		createButton = new Button("Create New Candidate", this);
		createButton.setStyleName("small");
		toolbar.addComponent(createButton);
		toolbar.setComponentAlignment(createButton, Alignment.MIDDLE_LEFT);
		
		deleteButton = new Button("Delete Selected Candidates", this);
		deleteButton.setStyleName("small");
		deleteButton.setEnabled(false);
		toolbar.addComponent(deleteButton);
		toolbar.setComponentAlignment(deleteButton, Alignment.MIDDLE_LEFT);
		
		addComponent(toolbar);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		if (event.getButton() == createButton) {			
			onCreateButtonClick();
        } else if (event.getButton() == deleteButton) {        	
        	onDeleteButtonClick();
        } else if (event.getButton() == importButton) {        	
        	onImportButtonClick();
        }
	}

	private void onDeleteButtonClick() {
		
		Dialog dialog = new Dialog(Dialog.Mode.YESNOCANCEL, "Unsaved Changes", "You have not saved this report. Do you want to save or discard any changes you've made to this report?", new Dialog.ResponseListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void response(ResponseEvent event) {
				
				if(event.isYes()) {
					
					final Set<Object> ids = new HashSet<Object>(selectedIds);
					ids.add(new Object());
					for (Object id : ids) {
						candidates.removeItem(id);
					}
					
					table.select(table.getNullSelectionItemId());
				}
			}
		});
		dialog.setYesCaption("Save");
		dialog.setNoCaption("Don't Save");
		dialog.setWidth(408L, Unit.PIXELS);
		
		getUI().addWindow(dialog);
	}

	private void onCreateButtonClick() {

		CandidateForm form = new CandidateForm(candidates.createEntityItem(new Candidate()));
		form.center();
		
		getUI().addWindow(form);
	}

	private void onImportButtonClick() {
		
		Dialog dialog = new Dialog(Dialog.Mode.OKCANCEL, "Import CV", "", new Dialog.ResponseListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void response(ResponseEvent event) {
				
				if(event.isYes()) {
					importCV();
				}
			}
		});
		dialog.setWidth(408L, Unit.PIXELS);
		
		getUI().addWindow(dialog);
	}
	
	private static InputStream loadResource(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
	
	private void importCV() {
		try {
			File input = new File("/tmp/input.html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
			//id = hh.ru id
			
			//date
			
			//firstName middleName lasName
			
			//vacancy
			
			//birthDate
			
			//residence
			
			//languages
			
			//education
			
			//career|employmentHistory
			
			//jobSkills
			
			//desiredSalary
			
			
		} catch (Exception e) {
	    	log.info(e.getMessage());
	    }
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		
		if (event.isDoubleClick()) {
			
			onEditButtonClick();
		}
	}

	private void onEditButtonClick() {
		
		Object id = selectedIds.iterator().next();
		
		CandidateForm form = new CandidateForm(candidates.getItem(id));
		form.center();
		
		getUI().addWindow(form);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		
		Object itemIds = event.getProperty().getValue();
		
		selectedIds.clear();
		
		if(itemIds instanceof Set<?>) {
			selectedIds.addAll((Set<?>)itemIds);
		}

		deleteButton.setEnabled(!selectedIds.isEmpty());
	}

	@Override
	public void enter(ViewChangeEvent e) {
		// TODO Auto-generated method stub		
	}
}
