package by.tretiak.demo.controller;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.model.Company;
import by.tretiak.demo.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController = new CompanyController();

    @Mock
    private CompanyService companyService = new CompanyService();

    private ObjectMapper mapper = new ObjectMapper();

    private static final String COMPANY_NAME = "BIT-electro";


    @Before
    public void saveCompany() throws ObjectNotFoundException {
        Company company = new Company();
        company.setId(1);
        company.setName(COMPANY_NAME);
        company.setIsEnable(true);
        Mockito.doReturn(company).when(this.companyService).getById(company.getId());
    }

    @Test
    public void getCompanyByIdTest() throws ObjectNotFoundException {
        Company company = new Company();
        company.setId(1);
        company.setName(COMPANY_NAME);
        company.setIsEnable(true);
        Company requestedCompany = this.companyController.getCompanyById(company.getId());
        Assert.assertEquals(company, requestedCompany);
    }

}
