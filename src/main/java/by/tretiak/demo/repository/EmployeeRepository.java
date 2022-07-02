package by.tretiak.demo.repository;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tretiak.demo.model.user.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    default Employee addPersonalCard(int employeeId, Card card) throws ObjectNotFoundException {
        Employee employee = findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
        employee.setPersonalCard(card);
        return save(employee);
    }

    default Employee addCorporateCard(int employeeId, Card card) throws ObjectNotFoundException {
        Employee employee = findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
        employee.setPersonalCard(card);
        return save(employee);
    }


}
