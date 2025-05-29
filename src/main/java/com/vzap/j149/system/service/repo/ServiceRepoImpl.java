package com.vzap.j149.system.service.repo;

import com.vzap.j149.system.service.model.Service;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)  // Add this for transaction management
public class ServiceRepoImpl implements ServiceRepo {

    @PersistenceContext(unitName = "J149PU")
    private EntityManager em;

    @Override
    public Optional<Service> save(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        try {
            if (service.getServiceId() == null) {
                em.persist(service);
            } else {
                em.merge(service);
            }
            return Optional.of(service);
        } catch (Exception e) {
            // Log the exception
            return Optional.empty();
        }
    }

    @Override
    public Optional<Service> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        try {
            return Optional.ofNullable(em.find(Service.class, id));
        } catch (Exception e) {
            // Log the exception
            return Optional.empty();
        }
    }

    @Override
    public List<Service> findAll() {
        try {
            TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s", Service.class);
            return query.getResultList();
        } catch (Exception e) {
            // Log the exception
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Service> update(Service service) {
        if (service == null || service.getServiceId() == null) {
            throw new IllegalArgumentException("Service and service ID cannot be null");
        }
        try {
            Service updated = em.merge(service);
            return Optional.of(updated);
        } catch (Exception e) {
            // Log the exception
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        try {
            Service service = em.find(Service.class, id);
            if (service != null) {
                em.remove(service);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Log the exception
            return false;
        }
    }

//    @Override
//    public Optional<Service> findById(Long id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}