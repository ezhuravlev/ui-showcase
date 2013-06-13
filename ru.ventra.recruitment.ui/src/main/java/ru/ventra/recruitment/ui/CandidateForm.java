package ru.ventra.recruitment.ui;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ru.ventra.recruitment.domain.Candidate;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable
public class CandidateForm extends Window implements Button.ClickListener, FieldGroup.CommitHandler {
	private static final long serialVersionUID = 1L;
	
	protected static final String SAVE_BUTTON_CAPTION = "Save";
	
	protected static final Collection<String> DEFAULT_VISIBLE_FIELDS = Arrays.asList("id", "firstName", "lastName", "middleName");

	private EntityItem<Candidate> item;	
	private BeanFieldGroup<Candidate> fieldGroup;
	
	private Button saveButton;
	private Button cancelButton;
	
	
	
	public CandidateForm(EntityItem<Candidate> candidateItem) {
		super(null, new VerticalLayout());
		
		setClosable(false);
		setResizable(false);
		
		VerticalLayout layout = (VerticalLayout)getContent();
		layout.setSpacing(true);
		
		item = candidateItem;
		
		BeanItem<Candidate> candidateBeanItem = new BeanItem<Candidate>(item.getEntity());
		fieldGroup = new BeanFieldGroup<Candidate>(Candidate.class);
		fieldGroup.addCommitHandler(this);
		fieldGroup.setItemDataSource(candidateBeanItem);
		
		FormLayout form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);
		
		
		AbstractTextField firstName = (AbstractTextField) fieldGroup.buildAndBind("First Name", "firstName");
		firstName.setNullRepresentation("");
		firstName.addValidator(new BeanValidator(Candidate.class, "firstName"));
		form.addComponent(firstName);
		
		AbstractTextField lastName = (AbstractTextField) fieldGroup.buildAndBind("Last Name", "lastName");
		lastName.setNullRepresentation("");
		lastName.addValidator(new BeanValidator(Candidate.class, "lastName"));
		form.addComponent(lastName);
		
		AbstractTextField middleName = (AbstractTextField) fieldGroup.buildAndBind("Middle Name", "middleName");
		middleName.setNullRepresentation("");
		middleName.addValidator(new BeanValidator(Candidate.class, "middleName"));
		form.addComponent(middleName);
		
		layout.addComponent(form);
		
		HorizontalLayout footer = new HorizontalLayout();		
		footer.setStyleName("footer");
		footer.setMargin(true);
		footer.setWidth(100L, Unit.PERCENTAGE);
		
		saveButton = new Button("OK", this);
		saveButton.setStyleName("wide default");		
		footer.addComponent(saveButton);
		footer.setComponentAlignment(saveButton, Alignment.MIDDLE_RIGHT);

		cancelButton = new Button("Cancel", this);
		cancelButton.setStyleName("wide default");		
		footer.addComponent(cancelButton);
		footer.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);

		layout.addComponent(footer);
        
		/*
		
		fieldGroup = new BeanFieldGroup<Candidate>(Candidate.class);
		fieldGroup.setItemDataSource(new BeanItem<Candidate>(new Candidate()));
		layout.addComponent(fieldGroup.buildAndBind("firstName", "firstName"));
		layout.addComponent(fieldGroup.buildAndBind("lastName", "lastName"));
		layout.addComponent(fieldGroup.buildAndBind("middleName", "middleName"));

        saveButton = new Button(SAVE_BUTTON_CAPTION, this);
        layout.addComponent(saveButton);
        
        cancelButton = new Button(CANCEL_BUTTON_CAPTION, this);
        layout.addComponent(cancelButton);
        
        setContent(layout);        
        setCaption(buildCaption());*/
	}

    /**
     * @return the caption of the editor window
     */
	protected String buildCaption() {
    	
        return String.format("%s %s", item.getItemProperty("firstName").getValue(), item.getItemProperty("lastName").getValue());
    }

	@Override
	public void buttonClick(ClickEvent event) {
		
		if (saveButton == event.getButton()) {			
			onSaveClick(event);			
        } else if (cancelButton == event.getButton()) {        	
        	onCancelClick(event);
        }
	}
    
    private void onSaveClick(ClickEvent event) {
        
		try {
			
			fieldGroup.commit();
			
	        close();
	        
		} catch (CommitException e) {
			// nothing to do, form must properly handle this
		}
    }
    
    private void onCancelClick(ClickEvent event) {
    	
    	fieldGroup.discard();
		
        close();    	
    }
    
    public void addCommitHandler(CommitHandler commitHandler) {
    	fieldGroup.addCommitHandler(commitHandler);
	}
	
	public void removeCommitHandler(CommitHandler commitHandler) {
    	fieldGroup.removeCommitHandler(commitHandler);
	}

	@Override
	public void preCommit(CommitEvent commitEvent) throws CommitException {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void postCommit(CommitEvent commitEvent) throws CommitException {

		Item updatedItem = commitEvent.getFieldBinder().getItemDataSource();
		
		JPAContainer<Candidate> container = (JPAContainer<Candidate>)item.getContainer();
		
		container.setWriteThrough(false);
		
		if(!item.isPersistent()) {
			item = container.getItem(container.addItem());
		}
		
		//FIXME: foreignKey fields
		for (Object id : updatedItem.getItemPropertyIds()) {
			item.getItemProperty(id).setValue(updatedItem.getItemProperty(id).getValue());
		}
		
		item.commit();
		
		container.setWriteThrough(true);
	}
}
