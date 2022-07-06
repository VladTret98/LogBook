package by.tretiak.demo.service;

import by.tretiak.demo.model.Company;
import by.tretiak.demo.repository.CompanyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableJpaRepositories(basePackages={"by.tretiak.demo.repository"})
public class CompanyServiceTest {

    @Autowired
    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository repository;

    @Test
    public void addCompany() {
        Company company = new Company();
        company.setName("BITasdasd");
        company.setIsEnable(true);

        ResponseEntity entity = companyService.addNewCompany(company);
        Company savedCompany = (Company) entity.getBody();
        boolean wasCompanySaved = savedCompany.getId() != 0;
        Assert.assertTrue(wasCompanySaved);
    }






}
