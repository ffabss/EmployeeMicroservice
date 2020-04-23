package com.asdf.managers;

import com.asdf.dataObjects.Employee;
import com.asdf.dataObjects.EmployeeEntity;
import com.asdf.dataObjects.EmployeeResource;
import com.asdf.database.EmployeeRepository;
import com.asdf.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeManager {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        List<Employee> emps = new ArrayList<>();
        for (EmployeeEntity empe : employeeRepository.findAll()) {
            emps.add(convertEntityToEmp(empe));
        }
        return emps;
    }

    public Employee addEmployee(Employee emp) {
        EmployeeEntity empe = convertEmpToEntity(emp);
        EmployeeEntity res = employeeRepository.save(empe);
        return convertEntityToEmp(res);
    }

    private EmployeeEntity convertEmpToEntity(Employee emp) {
        EmployeeEntity empe = new EmployeeEntity();
        if (emp.getId() != -1) {
            empe.setId(emp.getId());
        }
        empe.setLatitude(emp.getLatitude());
        empe.setLongitude(emp.getLongitude());
        empe.setName(emp.getName());

        return empe;
    }

    private Employee convertEntityToEmp(EmployeeEntity empe) {
        Employee emp = new Employee();
        emp.setId(empe.getId());
        emp.setLatitude(empe.getLatitude());
        emp.setLongitude(empe.getLongitude());
        emp.setName(empe.getName());

        return emp;
    }

    public Employee getEmployee(int employeeId) throws ResourceNotFoundException {
        Optional<EmployeeEntity> emp = employeeRepository.findById(employeeId);
        if (!emp.isPresent()) {
            throw new ResourceNotFoundException(String.format("there is no employee with the id %d", employeeId));
        }
        return convertEntityToEmp(emp.get());
    }

    public Employee deleteEmployee(int employeeId) throws ResourceNotFoundException {
        Optional<EmployeeEntity> emp = employeeRepository.findById(employeeId);
        if (!emp.isPresent()) {
            throw new ResourceNotFoundException(String.format("there is no employee with the id %d", employeeId));
        }
        EmployeeEntity empe = emp.get();
        employeeRepository.deleteById(employeeId);

        return convertEntityToEmp(empe);
    }

    public Employee putEmployee(int empId, Employee emp) {
        Optional<EmployeeEntity> emp_old = employeeRepository.findById(empId);
        Employee res = new Employee();
        if (emp_old.isPresent()) {
            res = convertEntityToEmp(emp_old.get());
        }

        EmployeeEntity empe = convertEmpToEntity(emp);
        empe.setId(empId);
        employeeRepository.save(empe);
        return res;
    }

    public List<Employee> getEmployees(int skip, int amount) {
        List<Employee> sers = new ArrayList<>();

        CriteriaBuilder criteriaBuilder = entityManager
                .getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> criteriaQuery = criteriaBuilder
                .createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> from = criteriaQuery.from(EmployeeEntity.class);
        CriteriaQuery<EmployeeEntity> select = criteriaQuery.select(from);
        TypedQuery<EmployeeEntity> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(amount);
        List<EmployeeEntity> resultList = typedQuery.getResultList();

        for (EmployeeEntity sere : resultList) {
            sers.add(convertEntityToEmp(sere));
        }
        return sers;
    }
}
