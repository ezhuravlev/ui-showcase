package ru.ventra.recruitment.ui.jpacontainer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.provider.BatchableLocalEntityProvider;


@Configurable
public class TransactionalEntityProvider<T> extends BatchableLocalEntityProvider<T> {

    public TransactionalEntityProvider(Class<T> entityClass, EntityManager em) {
        super(entityClass);
        setEntityManager(em);
        setTransactionsHandledByProvider(false);
    }

    @PostConstruct
    public void init() {
        /*
         * The entity manager is transaction scoped, which means that the entities will be automatically detached when the transaction is
         * closed. Therefore, we do not need to explicitly detach them.
         */
        setEntitiesDetached(false);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    protected void runInTransaction(Runnable operation) {
        super.runInTransaction(operation);
    }

    @Override
    public T updateEntity(final T entity) {
        return super.updateEntity(entity);
    }

    @Override
    public T addEntity(final T entity) {
        return super.addEntity(entity);
    }

    @Override
    public void removeEntity(final Object entityId) {
        super.removeEntity(entityId);
    }

    @Override
    public void updateEntityProperty(final Object entityId, final String propertyName, final Object propertyValue)
            throws IllegalArgumentException {
        super.updateEntityProperty(entityId, propertyName, propertyValue);
    }
}