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

    @RequestMapping(value ="/employees", method = RequestMethod.GET)
    public HttpEntity<List<EmployeeResource>> getAllEmployees() {
        return new HttpEntity<>(employeeDataService.getEmployeeResources());
    }

    @RequestMapping(value ="/employees/{empId}", method = RequestMethod.GET)
    public HttpEntity<EmployeeResource> getEmployee(@PathVariable int empId) {
        return new HttpEntity<>(employeeDataService.getEmployee(empId));
    }

    @RequestMapping(value ="/employees/{empId}", method = RequestMethod.DELETE)
    public HttpEntity<EmployeeResource> deleteEmployee(@PathVariable int empId) {
        return new HttpEntity<>(employeeDataService.deleteEmployee(empId));
    }

    @RequestMapping(value ="/employees/{empId}", method = RequestMethod.PUT)
    public HttpEntity<EmployeeResource> putEmployee(@PathVariable int empId,@RequestBody EmployeeDto employeeDto) {
        return new HttpEntity<>(employeeDataService.putEmployee(empId,employeeDto));
    }

    @RequestMapping(value ="/employees", method = RequestMethod.POST)
    public EmployeeResource addEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeDataService.addEmployeeDto(employeeDto);
    }
}