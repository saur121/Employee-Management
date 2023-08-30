package com.saurabh.em.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import com.saurabh.em.model.Employee;
import com.saurabh.em.repository.EmployeeRepository;

import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public List<Employee> getAllEmployees(){
		 return employeeRepository.findAll();
	}
	
	@Override
	public void saveEmployee(Employee employee) {
		 this.employeeRepository.save(employee);
	}
	
	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
	/*	Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();   */

        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? sort.ascending() : sort.descending();
		
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}
	
	
	
   /* @Override
    public List<Employee> searchEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstNameContaining(firstName);
    }   */
  
	/*
    @Override
    public void uploadFile(Long employeeId, MultipartFile file) {
    	 if(!file.isEmpty()) {
    		 try {
    			 
    			 Employee employee = getEmployeeById(employeeId);
    			 
    			 if(employee != null) {
    				// employee.setFileName(file.getOriginalFilename());
    				 
    				 byte[] imageData = extractImageFromDicom(file.getInputStream());
    				 
    				 if(imageData != null) {
    				 
    				// employee.setFileData(file.getBytes());
    				 employee.setFileUploaded(true);
    				 employee.setFileName(file.getOriginalFilename());
    				 employee.setFilePath(filePath.toString());
    				 
    				 saveEmployee(employee);
    				 }
    			 }
    		 }catch (IOException e) {
    			 e.printStackTrace();
    		 }
    	 }
    }  */
	
	
    
    
    private byte[] extractImageFromDicom(InputStream dicomInputStream) throws IOException {
        try (ImageInputStream iis = ImageIO.createImageInputStream(dicomInputStream)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(iis);
                BufferedImage bufferedImage = reader.read(0);

                // Convert BufferedImage to byte array (JPEG format)
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }  
    
   /* public ResponseEntity<Resource> downloadFile(Long employeeId){
        Employee employee = getEmployeeById(employeeId);
        
        if(employee != null && employee.isFileUploaded()) {
        	ByteArrayResource resource = new ByteArrayResource(employee.getFileData());
        	HttpHeaders headers = new HttpHeaders();
        	headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + employee.getFileName());
        	
        	return ResponseEntity.ok()
        			.headers(headers)
        			.contentLength(employee.getFileData().length)
        			.contentType(MediaType.APPLICATION_OCTET_STREAM)
        			.body(resource);
        }
        else {
        	return ResponseEntity.notFound().build();
        }
    }
        */
    @Override
    public List<Employee> getEmployeesByName(String firstName){
    	return employeeRepository.findByFirstNameContaining(firstName);
    }
}
