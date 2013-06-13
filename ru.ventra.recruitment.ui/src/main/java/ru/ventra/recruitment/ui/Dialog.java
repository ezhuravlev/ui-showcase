package ru.ventra.recruitment.ui;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.util.ReflectTools;

public final class Dialog extends Window implements ClickListener {
    private static final long serialVersionUID = 1L;
    
    public static final String OK_CAPTION = "OK";    
    public static final String CANCEL_CAPTION = "Cancel";    
    public static final String YES_CAPTION = "Yes";
    public static final String NO_CAPTION = "No";
    
    public enum Mode {
    	OK,
    	OKCANCEL,
    	YESNOCANCEL
    }
    
    private Label textLabel;
    private Button yesButton;
	private Button noButton;
    private Button cancelButton;
    
    public Dialog(Dialog.Mode mode, String caption, String text, ResponseListener listener) {
    	
    	this(mode, caption, text);
        
        addResponseListener(listener);    	
    }
    
    public Dialog(Dialog.Mode mode, String caption, String text) {
        super(caption, new VerticalLayout());
        
        setStyleName("dialog");
        setModal(true);
        setClosable(false);
        setResizable(false);
        
    	VerticalLayout layout = (VerticalLayout) getContent();
    	layout.setSpacing(true);
    	layout.setMargin(true);
    	
    	if(null != text) {
	    	textLabel = new Label(text);
	        layout.addComponent(textLabel);
    	}

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth(100L, Unit.PERCENTAGE);
        buttonLayout.setSpacing(true);
        
        noButton = new Button(NO_CAPTION, this);
        noButton.setStyleName("small");
        buttonLayout.addComponent(noButton);
        buttonLayout.setExpandRatio(noButton, 1);
        
        cancelButton = new Button(CANCEL_CAPTION, this);
        cancelButton.setStyleName("small");
        buttonLayout.addComponent(cancelButton);
        
        yesButton = new Button(OK_CAPTION, this);
        yesButton.setStyleName("small default");
        buttonLayout.addComponent(yesButton);
        
        switch(mode) {    	
	    	case YESNOCANCEL:
	    		yesButton.setCaption(YES_CAPTION);
	    		break;
        	case OKCANCEL:
        		noButton.setVisible(false);
                buttonLayout.setComponentAlignment(yesButton, Alignment.MIDDLE_RIGHT);
        		break;
        	case OK:
        		noButton.setVisible(false);
        		cancelButton.setVisible(false);
                buttonLayout.setComponentAlignment(yesButton, Alignment.MIDDLE_CENTER);
		        break;
        }
        
        layout.addComponent(buttonLayout);
    }
    
    public void setOkCaption(String okCaption) {
    	setYesCaption(okCaption);
    }
    
    public void setYesCaption(String yesCaption) {
    	if(null != yesButton) {
    		yesButton.setCaption(yesCaption);
    	}
    }
    
    public void setNoCaption(String noCaption) {
    	if(null != noButton) {
    		noButton.setCaption(noCaption);
    	}
    }
    
    public void setCancelCaption(String cancelCaption) {
    	if(null != cancelButton) {
    		cancelButton.setCaption(cancelCaption);
    	}
    }
    
    public void setQuestion(String question) {
    	if(null != this.textLabel) {
    		this.textLabel.setCaption(question);
    	}
    }
    
    @Override
    public void buttonClick(final ClickEvent event) {
    	
    	if(event.getSource() == yesButton) {
        	fireResponse(ResponseEvent.YES);
    	} else if(event.getSource() == noButton) {
        	fireResponse(ResponseEvent.NO);
    	} else if(event.getSource() == noButton) {
        	fireResponse(ResponseEvent.CANCEL);
    	}
    	
    	getUI().removeWindow(this);
    }
    
    public void addResponseListener(ResponseListener listener) {
        addListener(ResponseEvent.class, listener, ResponseListener.RESPONSE_METHOD);
    }

    protected void fireResponse(byte response) {
        fireEvent(new Dialog.ResponseEvent(this, response));
    }
    
    public static class ResponseEvent extends Component.Event {
		private static final long serialVersionUID = 1L;
		
		public static final byte YES = 0x1;		
		public static final byte NO = 0x2;		
		public static final byte CANCEL = 0x3;
		
		private byte response;
    	
    	public ResponseEvent(Component source, byte response) {
    		
            super(source);
            
            this.response = response;
        }
    	
    	public boolean isOk() {
    		return isYes();
    	}
    	
    	public boolean isCancel() {
    		return response == CANCEL;
    	}
    	
    	public boolean isYes() {
    		return response == YES;
    	}
    	
    	public boolean isNo() {
    		return response == NO;
    	}
    }
    
    public interface ResponseListener extends Serializable {

        public static final Method RESPONSE_METHOD = ReflectTools.findMethod(ResponseListener.class, "response", ResponseEvent.class);
        
        public void response(ResponseEvent event);
    }
}
