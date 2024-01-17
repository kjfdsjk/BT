package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByNameContaining(String name);
    List<Employee> findAllBySalaryBetween(double lowSalary,double highSalary);
    List<Employee> findAllByOrderBySalary();
    Page<Employee> findAllBy(Pageable pageable);

}
