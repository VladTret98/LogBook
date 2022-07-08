package by.tretiak.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tretiak.demo.model.user.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


}
