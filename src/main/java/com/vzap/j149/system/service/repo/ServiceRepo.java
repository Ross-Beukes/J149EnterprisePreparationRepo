package com.vzap.j149.system.service.repo;

import com.vzap.j149.system.service.model.Service;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ServiceRepo {

    @PersistenceContext(unitName = "J149PU")
    private EntityManager em;

    public void save(Service service) {
        if (service.getServiceId() == null) {
            em.persist(service);
        } else {
            em.merge(service);
        }
    }

    public Service findById(Long id) {
        return em.find(Service.class, id);
    }

    public List<Service> findAll() {
        TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s", Service.class);
        return query.getResultList();
    }

    public void update(Service service) {
        em.merge(service);
    }

    public void delete(Long id) {
        Service service = findById(id);
        if (service != null) {
            em.remove(service);
        }
    }
}