package by.tretiak.demo.repository;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyWorkersRepository {

    @Autowired
    private BookKeeperRepository bookKeeperRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee addEmployee(Employee employee, int companyId) throws ObjectNotFoundException {
        Company company = this.companyRepository.findById(companyId).orElseThrow(()
                -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
        employee.setCompany(company);
        employee = this.employeeRepository.save(employee);
        company.getEmployees().add(employee);
        this.companyRepository.save(company);
        return employee;
    }

    @Transactional
    public BookKeeper addKeeper(BookKeeper keeper, int companyId) throws ObjectNotFoundException {
        Company company = this.companyRepository.findById(companyId).orElseThrow(()
                -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
        keeper.setCompany(company);
        this.bookKeeperRepository.save(keeper);
        company.getKeepers().add(keeper);
        this.companyRepository.save(company);
        return keeper;
    }



}
