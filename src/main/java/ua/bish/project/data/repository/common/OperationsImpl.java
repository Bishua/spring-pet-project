package ua.bish.project.data.repository.common;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * most standard implementation for CRUD
 */
@SuppressWarnings("unchecked")
public class OperationsImpl<T, PK extends Serializable> implements GenericRepository<T, PK> {
    private Class<T> type;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public PK create(T newInstance) {
        return (PK) getCurrentSession().save(newInstance);
    }

    public T read(PK id) {
        return getCurrentSession().get(type, id);
    }

    public void update(T transientObject) {
        getCurrentSession().update(transientObject);
    }

    public void delete(T persistentObject) {
        getCurrentSession().delete(persistentObject);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void setType(Class type) {
        this.type = type;
    }
}