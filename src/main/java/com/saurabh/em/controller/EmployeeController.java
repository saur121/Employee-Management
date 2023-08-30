package com.saurabh.em.controller;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import com.saurabh.em.model.Employee;
import com.saurabh.em.model.User;
import com.saurabh.em.repository.EmployeeRepository;
import com.saurabh.em.service.EmployeeService;

@Controller
public class EmployeeController {
    
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
	
/*	@PostMapping("/uploadFile/{employeeId}")
	public String uploadFile(@PathVariable Long employeeId, @RequestParam("file") MultipartFile file) {
		
		employeeService.uploadFile(employeeId, file);
		
		return "redirect:/";
	}  */
	
	@PostMapping("/uploadFile/{employeeId}")
	public String uploadFile(@PathVariable Long employeeId, @RequestParam("file") MultipartFile file) {
	    if (!file.isEmpty()) {
	        try {
	            // Save file to local storage
	            Path filePath = Paths.get("D:/sts/Image", employeeId.toString(), file.getOriginalFilename());
	            Files.createDirectories(filePath.getParent()); // Create directories if not exist
	            Files.write(filePath, file.getBytes());

	            // Update employee with file path
	            Employee employee = employeeService.getEmployeeById(employeeId);
	            employee.setFileUploaded(true);
	            employee.setFileName(file.getOriginalFilename());
	            employee.setFilePath(filePath.toString()); // Set the file path

	            employeeService.saveEmployee(employee);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return "redirect:/";
	}
	
	/*@GetMapping("/downloadFile/{employeeId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long employeeId){
		return employeeService.downloadFile(employeeId);
	}*/
	
	@GetMapping("/fupload/{id}")
	public String uploadDownloadFile(@PathVariable int id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		
		
		model.addAttribute("employee",employee);
		
		return "fupload";
	}
}
	
  /*  @GetMapping("/displayImage/{employeeId}")
    public ResponseEntity<Resource> displayImage(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee != null && employee.isFileUploaded()) {
            byte[] imageData = employee.getFileData();
            //MediaType contentType = MediaTypeFactory.getMediaType(imageData).orElse(MediaType.APPLICATION_OCTET_STREAM);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            ByteArrayResource resource = new ByteArrayResource(imageData);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(imageData.length)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }  */

