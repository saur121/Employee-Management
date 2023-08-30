package com.saurabh.em.controller;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saurabh.em.model.Employee;
import com.saurabh.em.repository.EmployeeRepository;
import com.saurabh.em.service.EmployeeService;

@RestController
public class SearchController {
     
    private final EmployeeService employeeService;

    @Autowired
    public SearchController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

   /* @GetMapping("/search/{query}")
    public List<Employee> search(@PathVariable String query) {
        //System.out.println(query);

      //  List<Employee> employees = employeeService.searchEmployeesByFirstName(query);
      //  return ResponseEntity.ok(employees);
    	
    	return employeeService.searchEmployeesByFirstName(query);
    }*/
       
    
  /* @GetMapping("/nativeQuery/{name}")
   public List<Employee> getAll(@PathVariable String firstName){
	   return employeeService.getAllEmployeeThroughNativeQuery(firstName);
   }*/
    
//    @GetMapping("/find")
//    public List<Employee> searchEmployees(@RequestParam String firstName){
//    	return employeeService.getEmployeesByName(firstName);
//    }
    
    
    @GetMapping("/find/{firstName}")
    public List<Employee> searchEmployees(@PathVariable String firstName){
    	return employeeService.getEmployeesByName(firstName);
    }
}
