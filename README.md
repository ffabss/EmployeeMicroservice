# REST - API for our [Service-Manager](https://service-manager.web.app/)

The optional request parameter boolean `address` indicates whether the address should be included or not.

## Employees - https://employee-microservice.herokuapp.com/serviceBackend

### /employees 

* __GET__ returns all employees in form of [EmployeeResources](#Employee)
* __POST__ adds the employee from the request body in form of a [EmployeeDto](#Employee) and returns the saved [EmployeeResource](#Employee)
* __DELETE__ deletes all employees and returns them in form of [EmployeeResources](#Employee)

### /employees/{skip}/{amount} 

* __GET__ skips the first `skip` employees and then returns the `amount` following employees in form of [EmployeeResources](#Employee)

### /employees/{id} 

* __GET__ returns the employee with the specified `id` in form of a [EmployeeResource](#Employee)
* __PUT__ saves the employee from the request body, in form of a [EmployeeDto](#Employee), with the specified `id` and returns the old employee with the specified id in form of a [EmployeeResource](#Employee)
* __DELETE__ removes the employee with the specified `id` and returns him in form of a [EmployeeResource](#Employee)

### /countEmployees

* __GET__ returns the number of available employees

## Classes

### Address from [LocationIQ](https://locationiq.com/docs-html/index.html#reverse_response)
    public class AddressResource {
        private String house_number;
        private String road;
        private String hamlet;
        private String village;
        private String county;
        private String state;
        private String postcode;
        private String country;
        private String country_code;
        private String town;
        private String street;
        private String city;
    }

### Employee
    public class EmployeeDto {
        private String name;
        private String address;
    }

    public class EmployeeResource {
        private int id;
        private String name;
        private AddressResource address;
        private String latitude;
        private String longitude;
    }
