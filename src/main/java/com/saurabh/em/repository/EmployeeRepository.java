package com.saurabh.em.repository;


import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saurabh.em.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //List<Employee> findByFirstNameContaining(String firstName);
    
   // Optional<Employee> findByFileName(String fileName);
    
    
	/*@Query(value = "select * from employee where first_name = ?1", nativeQuery = true)
	public List<Employee> getEmployeeByName(@Param("first_name") String firstName);*/
    
    List<Employee> findByFirstNameContaining(String firstName);
}
