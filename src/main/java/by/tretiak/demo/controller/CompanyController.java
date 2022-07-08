package by.tretiak.demo.controller;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.pojo.ValidationErrorPojo;
import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.service.CompanyService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/companies")
@NoArgsConstructor
@Setter
public class CompanyController extends AbstractController{

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies() {
        return this.companyService.findAll();
    }

    @GetMapping(path = ID_PATH)
    public Company getCompanyById(@PathVariable(ID_PARAM) int id) throws ObjectNotFoundException {
        return this.companyService.getById(id);
    }

    @PostMapping(path = NEW_PATH)
    public Company addCompany(@Valid @RequestBody Company company, BindingResult bindingResult) throws ValidationException {
        validate(bindingResult);
        return this.companyService.addNewCompany(company);
    }

    @PatchMapping(path = ID_PATH)
    public MessageResponse setStatus(@PathVariable(ID_PARAM) int id, @RequestParam(name = STATUS_PARAM) boolean status) {
        return this.companyService.setStatus(id, status);
    }

}
