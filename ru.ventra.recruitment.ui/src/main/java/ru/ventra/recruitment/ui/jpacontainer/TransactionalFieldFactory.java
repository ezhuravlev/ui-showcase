package ru.ventra.recruitment.ui.jpacontainer;

import java.util.HashMap;

import javax.persistence.EntityManager;

import ru.ventra.recruitment.ui.upload.UploadField;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.addon.jpacontainer.provider.jndijta.CachingBatchableEntityProvider;
import com.vaadin.addon.jpacontainer.provider.jndijta.CachingMutableEntityProvider;
import com.vaadin.addon.jpacontainer.provider.jndijta.JndiJtaProvider;
import com.vaadin.addon.jpacontainer.util.EntityManagerPerRequestHelper;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class TransactionalFieldFactory extends FieldFactory implements FieldGroupFieldFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Class<?>, String[]> propertyOrders;
	private EntityManagerPerRequestHelper entityManagerPerRequestHelper;
	private HashMap<Class<?>, Class<? extends AbstractSelect>> multiselectTypes;
	private HashMap<Class<?>, Class<? extends AbstractSelect>> singleselectTypes;

	@Override
	protected JPAContainer<?> createJPAContainerFor(EntityContainer<?> referenceContainer, Class<?> type, boolean buffered) {
		JPAContainer<?> container = null;
		EntityProvider<?> referenceEntityProvider = referenceContainer.getEntityProvider();
		if (referenceEntityProvider instanceof JndiJtaProvider) {
			JndiJtaProvider<?> jndiProvider = (JndiJtaProvider) referenceEntityProvider;
			container = new JPAContainer(type);
			JndiJtaProvider entityProvider;
			if (buffered) {
				entityProvider = new CachingBatchableEntityProvider(type);
			} else {
				entityProvider = new CachingMutableEntityProvider(type);
			}
			entityProvider.setJndiAddresses(jndiProvider.getJndiAddresses());

			container.setEntityProvider(entityProvider);
		} else if (referenceEntityProvider instanceof TransactionalEntityProvider) {
			
			EntityManager em = referenceEntityProvider.getEntityManager();
			if (buffered) {
				container = JPAContainerFactory.makeBatchable(type, em);
				container.setEntityProvider((EntityProvider)referenceEntityProvider);
			} else {
//				container = JPAContainerFactory.make(type, em);
//				container.setEntityProvider((EntityProvider)referenceEntityProvider);
				container = CustomJPAContainer.makeWithEntityProvider(type, new TransactionalEntityProvider(type, em));
			}
		} else {
			EntityManager em = referenceEntityProvider.getEntityManager();
			if (buffered) {
				container = JPAContainerFactory.makeBatchable(type, em);
			} else {
				container = JPAContainerFactory.make(type, em);
			}
		}
		configureContainer(referenceContainer, container);
		return container;
	}

	@Override
	protected Field createOneToManyField(EntityContainer containerForProperty,
			Object itemId, Object propertyId, Component uiContext) {
		return new TransactionalMasterDetailEditor(this, containerForProperty, itemId,
				propertyId, uiContext);
	}

	@Override
	public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
		return null;
	}
}


class CustomJPAContainer extends JPAContainerFactory{
	
	public static <T> JPAContainer<T> makeWithEntityProvider(
            Class<T> entityClass, EntityProvider<T> entityProvider) {
        JPAContainer<T> container = new JPAContainer<T>(entityClass);
        container.setEntityProvider(entityProvider);
        return container;
    }
}