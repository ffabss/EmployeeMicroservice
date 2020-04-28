package com.asdf.database;


import com.asdf.dataObjects.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {
    @Query("select id from EmployeeEntity")
    public List<Integer> findAllIds();

    @Query("SELECT e FROM EmployeeEntity e ORDER BY e.id ASC")
    public List<EmployeeEntity> findAllOrderById();
}
