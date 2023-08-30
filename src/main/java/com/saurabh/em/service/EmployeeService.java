package com.saurabh.em.service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import com.saurabh.em.model.*;

public interface EmployeeService {
    
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeById(long id);
	
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	
	//List<Employee> searchEmployeesByFirstName(String firstName);
	
	void uploadFile(Long employeeId, MultipartFile file);
	
//	ResponseEntity<Resource> downloadFile(Long employeeId);
	
	//List<Employee> getAllEmployeeThroughNativeQuery(String firstName);
	
	List<Employee> getEmployeesByName(String firstName);
} 
