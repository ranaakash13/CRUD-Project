package com.work.app.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.work.app.model.Employee;
@Repository
public interface EmployeeJpaRepo extends JpaRepository<Employee, Long>{
	
}
