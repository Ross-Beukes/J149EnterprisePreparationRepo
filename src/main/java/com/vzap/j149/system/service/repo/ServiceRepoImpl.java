package com.vzap.j149.system.service.repo;

import com.vzap.j149.system.service.model.Service;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.QueryHint;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ServiceRepoImpl implements ServiceRepo {
    private static final Logger LOGGER = Logger.getLogger(ServiceRepoImpl.class.getName());
    private static final String SERVICE_ENTITY = "Service";

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
                em.flush(); // Force immediate insert to get generated ID
                return Optional.of(service);
            }
            Service merged = em.merge(service);
            return Optional.of(merged);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving service: " + e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Service> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(em.find(Service.class, id));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding service by id: " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Service> findAll() {
        return findPaginated(0, Integer.MAX_VALUE);
    }

    public List<Service> findPaginated(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }
        try {
            TypedQuery<Service> query = em.createQuery(
                            "SELECT s FROM Service s ORDER BY s.serviceId", Service.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .setHint("jakarta.persistence.query.timeout", 5000); // 5 second timeout
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all services", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Service> update(Service service) {
        if (service == null || service.getServiceId() == null) {
            throw new IllegalArgumentException("Service and service ID cannot be null");
        }
        try {
            if (!existsById(service.getServiceId())) {
                return Optional.empty();
            }
            Service updated = em.merge(service);
            em.flush(); // Force update to happen now
            return Optional.of(updated);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating service: " + service.getServiceId(), e);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        try {
            Service service = em.find(Service.class, id);
            if (service != null) {
                em.remove(service);
                em.flush(); // Force delete to happen now
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting service with id: " + id, e);
            return false;
        }
    }

    public boolean existsById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Service s WHERE s.serviceId = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if service exists with id: " + id, e);
            return false;
        }
    }

    public long count() {
        try {
            return em.createQuery("SELECT COUNT(s) FROM Service s", Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting services", e);
            return 0;
        }
    }
}