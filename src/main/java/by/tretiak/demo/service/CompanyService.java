package by.tretiak.demo.service;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
@Setter
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthService authService;

    public ResponseEntity<?> addNewCompany(Company company) {
            company = this.repository.save(company);
            return ResponseEntity.ok(company);
    }

    public ResponseEntity<?> getById(int id) {
        try {
            Company requestedCompany = this.repository.findById(id).orElseThrow(()
                    -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
            return ResponseEntity.ok(requestedCompany);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<?> addKeeper(BookKeeper keeper, int companyId) {
        try{
            Company company = this.repository.findById(companyId).orElseThrow(()
                    -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
            keeper.setCompany(company);
            keeper = (BookKeeper) this.authService.prepareNewUser(keeper);
            company.getKeepers().add(keeper);
            this.repository.save(company);
            return ResponseEntity.ok(MessageResponse.USER_CREATED);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<?> addEmployee(Employee employee, int companyId) {
        try{
            Company company = this.repository.findById(companyId).orElseThrow(()
                    -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
            employee.setCompany(company);
            employee = (Employee) this.authService.prepareNewUser(employee);
            company.getEmployees().add(employee);
            this.repository.save(company);
            return ResponseEntity.ok(MessageResponse.USER_CREATED);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<?> setStatus(int id, boolean status) {
        try {
            Company company = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                    .getMessage(ExceptionMessageSource.COMPANY_NOT_FOUND)));
            company.setIsEnable(status);
            company.getKeepers().forEach(bookKeeper -> bookKeeper.setEnable(status));
            company.getEmployees().forEach(employee -> employee.setEnable(status));
            this.repository.save(company);
            return ResponseEntity.ok(MessageResponse.SUCCESS);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public String findAll() {
        try {
            return this.mapper.writeValueAsString(this.repository.findAll());
        } catch (JsonProcessingException e) {
            return ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR);
        }
    }

}
