package com.asdf.database;


import com.asdf.dataObjects.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {
}
