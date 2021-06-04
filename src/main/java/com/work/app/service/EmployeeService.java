package com.work.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.work.app.model.Employee;

import io.minio.messages.Bucket;

public interface EmployeeService {
	
	public List<Employee> getAllEmployee();
	
	public List<Bucket> getAllBuckests();
	
	public Employee getById(Long id);
	
	public void addEmployee(Employee employee);
	
	public void deleteEmployee(Long id);

	void uploadFile(MultipartFile file);
	
}
