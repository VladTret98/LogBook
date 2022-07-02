package by.tretiak.demo.controller;

import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.pojo.ValidationError;
import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.service.CompanyService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private static final String NEW_EMPLOYEE_PATH = "/newemployee";

    private static final String NEW_KEEPER_PATH = "/newkeeper";

    private static final String COMPANY_ID_PARAM = "companyId";

    @GetMapping
    public String getCompanies() {
        return this.companyService.findAll();
    }

    @GetMapping(path = ID_PATH)
    public ResponseEntity<?> getCompanyById(@PathVariable(ID_PARAM) int id) {
        return this.companyService.getById(id);
    }

    @PostMapping(path = NEW_PATH)
    public ResponseEntity<?> addCompany(@Valid @RequestBody Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
        }
            return this.companyService.addNewCompany(company);
    }

    @PatchMapping(path = ID_PATH)
    public ResponseEntity<?> setStatus(@RequestParam(ID_PARAM) int id, @RequestParam(name = STATUS_PARAM) boolean status) {
        return this.companyService.setStatus(id, status);
    }

    @PostMapping(NEW_EMPLOYEE_PATH)
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Employee employee,
                              @RequestParam(name = COMPANY_ID_PARAM) @Min(1) int companyId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
        }
        return this.companyService.addEmployee(employee, companyId);
    }

    @PostMapping(path = NEW_KEEPER_PATH)
    public ResponseEntity<?> addKeeper(@RequestBody BookKeeper bookKeeper,
                                    @RequestParam(name = COMPANY_ID_PARAM) @Min(1) int companyId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
        }
            return this.companyService.addKeeper(bookKeeper, companyId);
    }




}
