package com.work.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.work.app.model.Employee;
import com.work.app.service.EmployeeService;

import io.minio.messages.Bucket;

@RestController
@RequestMapping("/emp")
@EnableCaching
public class EmployeeController {
	
	@Autowired
	EmployeeService empSer;
	
	@PostMapping("/save")
	public String addEmployee(@RequestBody Employee employee) {
		empSer.addEmployee(employee);
		return "Saved";
	}
	
	@GetMapping(value="/list")
	public List<Employee> list(){
		return empSer.getAllEmployee();
		
	}

	@GetMapping("/list/{id}")
	@Cacheable(value = "Employee", key ="#id")
	public Employee getById(@PathVariable("id") Long id) {
		return empSer.getById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") Long id) {
		empSer.deleteEmployee(id);
		return "Deleted " +id;
	}
	
	@PostMapping("/upload")
	public void uploadFile(@RequestParam("file") MultipartFile multipartFile) {
		empSer.uploadFile(multipartFile);
	}
	
	@GetMapping(path = "/buckets")
	public List<Bucket> listBuckets() {
	    return empSer.getAllBuckests();
	}
	
}
