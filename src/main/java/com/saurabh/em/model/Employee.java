package com.saurabh.em.model;

import java.io.IOException;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	
	 private String firstName;
	 
	 private String lastName;
	 
	 private String email;
	 
	 private boolean fileUploaded;
	 
	 private String fileName;
	 
		@Transient
		public MultipartFile file;
		
	private String filePath;
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
		
	
	/*	@Lob
		public byte[] fileData;
		
		public void setFile(MultipartFile file) throws IOException{
	        this.file = file;
	        if (file != null && !file.isEmpty()) {
	            this.fileData = file.getBytes();
	        } else {
	            this.fileData = null; // Set fileData to null when no file is uploaded
	        }
		} 
		*/
	
}
