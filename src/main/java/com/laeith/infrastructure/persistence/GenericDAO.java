package com.laeith.infrastructure.persistence;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<E> {
  protected final Logger LOG;

  private Class<E> entityType;

  @PersistenceContext
  protected EntityManager em;

  @SuppressWarnings("unchecked")
  public GenericDAO() {
    this.entityType = ((Class<E>) ((ParameterizedType) getClass()
       .getGenericSuperclass())
       .getActualTypeArguments()[0]);
    this.LOG = LogManager.getLogger(entityType);
  }

  public E save(E newInstance) {
    LOG.debug("DB Save: " + newInstance.toString());
    em.persist(newInstance);
    return newInstance;
  }

  public E saveOrUpdate(E entity) {
    LOG.debug("DB saveOrUpdate: " + entity.toString());
    em.merge(entity);
    return entity;
  }

  public Optional<E> get(long primaryId) {
    LOG.debug("DB get: " + primaryId);
    E entity = em.find(entityType, primaryId);
    return Optional.ofNullable(entity);
  }

  @SuppressWarnings("unchecked")
  public List<E> getAll() {
    LOG.debug("DB getting all for: " + entityType.getSimpleName());
    return em.createQuery("FROM " + entityType.getName()).getResultList();
  }

  public void delete(E entity) {
    LOG.debug("DB delete: " + entity.toString());
    em.remove(entity);
  }

  public void deleteById(Long entityId) {
    LOG.debug("DB deleteById: " + entityId.toString());
    get(entityId).ifPresent(this::delete);
  }

}

