package com.work.app.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.work.app.model.Employee;
@Repository
public interface EmployeeRepo extends ElasticsearchRepository<Employee, Long>{
	
}