package by.tretiak.demo.repository;

import by.tretiak.demo.model.Company;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void addCompany() {
        Company company = new Company();
        company.setId(1);
        company.setIsEnable(true);
        company.setName("BIT-electro");
        company.setEmployees(new ArrayList<>());
        company.setKeepers(new ArrayList<>());
        this.companyRepository.save(company);
    }


    @Test
    public void testFindByIdMethod() {
        Company company = new Company();
        company.setId(1);
        company.setIsEnable(true);
        company.setName("BIT-electro");
        company.setEmployees(new ArrayList<>());
        company.setKeepers(new ArrayList<>());

        Optional<Company> returnedCompany = this.companyRepository.findById(1);
        Company result = returnedCompany.orElseGet(() -> new Company());
        Assert.assertEquals(company, result);
    }

}
