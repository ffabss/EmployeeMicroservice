package com.asdf.dataServices;

import com.asdf.dataObjects.Employee;
import com.asdf.dataObjects.EmployeeDto;
import com.asdf.dataObjects.EmployeeResource;
import com.asdf.dataObjects.location.LongitudeLatitude;
import com.asdf.exceptions.ResourceNotFoundException;
import com.asdf.exceptions.rest.InvalidDataExceptionMS;
import com.asdf.exceptions.rest.ResourceNotFoundExceptionMS;
import com.asdf.managers.EmployeeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDataService {
    @Autowired
    private EmployeeManager employeeManager;
    @Autowired
    private LocationIQDataService locationIQDataService;

    public List<EmployeeResource> getEmployeeResources() {
        List<EmployeeResource> emps = new ArrayList<>();
        for (Employee emp : employeeManager.getEmployees()) {
            emps.add(empToResource(emp));
        }
        return emps;
    }

    private EmployeeResource empToResource(Employee emp) {
        EmployeeResource er = new EmployeeResource();
        er.setId(emp.getId());
        er.setAddress(locationIQDataService.getAddress(emp.getLongitude(), emp.getLatitude()));
        er.setName(emp.getName());
        return er;
    }

    private Employee empResToEmp(EmployeeResource empr) {
        Employee emp = new Employee();
        emp.setId(empr.getId());
        emp.setName(empr.getName());
        LongitudeLatitude location = locationIQDataService.getLongitudeLatitudeByAddress(empr.getAddress());
        emp.setLongitude(location.getLongitude());
        emp.setLatitude(location.getLatitude());
        return emp;
    }

    private Employee empDtoToEmp(EmployeeDto employeeDto) {
        Employee emp = new Employee();
        LongitudeLatitude location = locationIQDataService.getLongitudeLatitudeByAddress(employeeDto.getAddress());
        emp.setLongitude(location.getLongitude());
        emp.setLatitude(location.getLatitude());
        emp.setName(employeeDto.getName());

        return emp;
    }

    public EmployeeResource addEmployeeDto(EmployeeDto employeeDto) {
        checkEmployeeDto(employeeDto);

        Employee emp = empDtoToEmp(employeeDto);

        Employee res = employeeManager.addEmployee(emp);

        return empToResource(res);
    }

    public EmployeeResource getEmployee(int empId) {
        try {
            return empToResource(employeeManager.getEmployee(empId));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundExceptionMS(e);
        }
    }

    public EmployeeResource deleteEmployee(int empId) {
        try {
            return empToResource(employeeManager.deleteEmployee(empId));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundExceptionMS(e);
        }
    }

    private void checkEmployeeDto(EmployeeDto employeeDto) {
        if (isNullOrEmpty(employeeDto.getName())) {
            throw new InvalidDataExceptionMS("The name of the employee must be set");
        }
        if (isNullOrEmpty(employeeDto.getAddress())) {
            throw new InvalidDataExceptionMS("The address of the employee must be set");
        }
        if (employeeDto.getName().length() < 4) {
            throw new InvalidDataExceptionMS("The name must be minimum 4 long");
        }
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.equals("");
    }

    public EmployeeResource putEmployee(int empId, EmployeeDto employeeDto) {
        checkEmployeeDto(employeeDto);
        Employee res = employeeManager.putEmployee(empId, empDtoToEmp(employeeDto));
        return empToResource(res);
    }
}