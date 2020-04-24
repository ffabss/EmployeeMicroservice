package com.asdf;


import com.asdf.dataObjects.EmployeeDto;
import com.asdf.dataObjects.EmployeeResource;
import com.asdf.dataServices.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/serviceBackend")
public class RESTController {

    @Autowired
    private EmployeeDataService employeeDataService;


    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public HttpEntity<List<EmployeeResource>> resetEmployees() {
        return resetEmployees(10);
    }

    @RequestMapping(value = "/reset/{amount}", method = RequestMethod.GET)
    public HttpEntity<List<EmployeeResource>> resetEmployees(@PathVariable int amount) {
        return new HttpEntity<>(employeeDataService.resetEmployeeResources(amount));
    }

    @RequestMapping(value = "/validIds", method = RequestMethod.GET)
    public HttpEntity<List<Integer>> getValidIds() {
        return new HttpEntity<>(employeeDataService.getValidIds());
    }

    @RequestMapping(value = "/countEmployees", method = RequestMethod.GET)
    public HttpEntity<Long> countEmployees() {
        return new HttpEntity<>(employeeDataService.countEmployees());
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public HttpEntity<List<EmployeeResource>> getAllEmployees() {
        return new HttpEntity<>(employeeDataService.getEmployeeResources());
    }

    @RequestMapping(value = "/clearEmployees", method = RequestMethod.DELETE)
    public HttpEntity<List<EmployeeResource>> deleteAllEmployees() {
        return new HttpEntity<>(employeeDataService.deleteEmployeeResources());
    }

    @RequestMapping(value = "/employees/{skip}/{amount}", method = RequestMethod.GET)
    public HttpEntity<List<EmployeeResource>> getEmployees(@PathVariable int skip, @PathVariable int amount) {
        return new HttpEntity<>(employeeDataService.getEmployeeResources(skip, amount));
    }

    @RequestMapping(value = "/employees/{empId}", method = RequestMethod.GET)
    public HttpEntity<EmployeeResource> getEmployee(@PathVariable int empId) {
        return new HttpEntity<>(employeeDataService.getEmployee(empId));
    }

    @RequestMapping(value = "/employees/{empId}", method = RequestMethod.DELETE)
    public HttpEntity<EmployeeResource> deleteEmployee(@PathVariable int empId) {
        return new HttpEntity<>(employeeDataService.deleteEmployee(empId));
    }

    @RequestMapping(value = "/employees/{empId}", method = RequestMethod.PUT)
    public HttpEntity<EmployeeResource> putEmployee(@PathVariable int empId, @RequestBody EmployeeDto employeeDto) {
        return new HttpEntity<>(employeeDataService.putEmployee(empId, employeeDto));
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public EmployeeResource addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeDataService.addEmployeeDto(employeeDto);
    }
}
