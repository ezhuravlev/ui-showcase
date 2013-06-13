package ru.ventra.recruitment.ui.jpacontainer;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.MasterDetailEditor;
import com.vaadin.ui.Component;

public class TransactionalMasterDetailEditor extends MasterDetailEditor {
	private static final long serialVersionUID = 1L;

//    private final TransactionalFieldFactory fieldFactory;
//    private Class<?> referencedType;
//
//    final private Action add = new Action(getMasterDetailAddItemCaption());
//    final private Action remove = new Action(getMasterDetailRemoveItemCaption());
//    final private Action[] actions = new Action[] { add, remove };
//    private JPAContainer container;
//    private Table table;
//    private String backReferencePropertyId;
//    private Object masterEntity;
//    private final Object propertyId;
//    private final EntityContainer<?> containerForProperty;
//    private final Object itemId;
	
	
	public TransactionalMasterDetailEditor(FieldFactory fieldFactory,
			EntityContainer<?> containerForProperty, Object itemId,
			Object propertyId, Component uiContext) {
		super(fieldFactory, containerForProperty, itemId, propertyId, uiContext);
		//setWriteThrough(true);
	}

//	@Override
//	public MasterDetailEditor(FieldFactory fieldFactory,
//			EntityContainer<?> containerForProperty, Object itemId,
//			Object propertyId, Component uiContext) {
//		this.fieldFactory = fieldFactory;
//		this.containerForProperty = containerForProperty;
//		this.itemId = itemId;
//		this.propertyId = propertyId;
//		try {
//			masterEntity = containerForProperty.getItem(itemId).getEntity();
//		} catch (NullPointerException e) {
//			// TODO: handle exception
//		}
//
//		boolean writeThrough = true;
//		if (uiContext instanceof Form) {
//			Form f = (Form) uiContext;
//			writeThrough = f.isWriteThrough();
//		}
//		buildContainer(writeThrough);
//
//		buildLayout();
//
//		setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
//	}

}
