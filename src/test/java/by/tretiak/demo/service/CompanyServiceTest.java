package by.tretiak.demo.service;

import by.tretiak.demo.model.Company;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompanyServiceTest {

    @Mock
    private CompanyService companyService;

    private static final String COMPANY_NAME = "BIT-electro";

    @Before
    public void saveCompany() {
        Company company = new Company();
        company.setId(1);
        company.setName(COMPANY_NAME);
        company.setIsEnable(true);
        Mockito.when(this.companyService.addNewCompany(Mockito.any(Company.class))).thenReturn(company);
    }

    @Test
    public void testAddCompany() {
        Company company = new Company();
        company.setName("BIT-electro");
        company.setIsEnable(true);
        Company savedCompany = companyService.addNewCompany(company);
        boolean wasCompanySaved = savedCompany.getId() != 0;
        Assert.assertTrue(wasCompanySaved);
    }

}
