package com.asdf.database;


import com.asdf.dataObjects.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RestResource(exported = false)
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {
    @Query("select id from EmployeeEntity")
    List<Integer> findAllIds();

    @Query("SELECT e FROM EmployeeEntity e ORDER BY e.id ASC")
    List<EmployeeEntity> findAllOrderById();
}
