package by.tretiak.demo.service;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.repository.CompanyRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Setter
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private AuthService authService;

    public Company addNewCompany(Company company) {
        company = this.repository.save(company);
        return company;
    }

    public Company getById(int id) throws ObjectNotFoundException {
        try {
            Company requestedCompany = this.repository.findById(id).orElseThrow(()
                    -> new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
            return requestedCompany;
        } catch (ObjectNotFoundException e) {
            throw e;
        }
    }

    @Transactional
    public MessageResponse setStatus(int id, boolean status) {
        try {
            Company company = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                    .getMessage(ExceptionMessageSource.COMPANY_NOT_FOUND)));
            company.setIsEnable(status);
            company.getKeepers().forEach(bookKeeper -> bookKeeper.setEnable(status));
            company.getEmployees().forEach(employee -> employee.setEnable(status));
            this.repository.save(company);
            return new MessageResponse(MessageResponse.SUCCESS);
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }

    public List<Company> findAll() {
            return this.repository.findAll();
    }

}
