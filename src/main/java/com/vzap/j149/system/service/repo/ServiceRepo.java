
package com.vzap.j149.system.service.repo;

import com.vzap.j149.system.service.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepo {
    Optional<Service> save(Service service);
    Optional<Service> findById(Long id);
    List<Service> findAll();  // Changed return type
    Optional<Service> update(Service service);
    boolean delete(Long id);  // Changed to return boolean for success/failure
}
