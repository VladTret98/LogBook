package by.tretiak.demo.repository;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company saveAndFlush(Company company);

	default void setStatus(Integer id, Boolean isEnable) throws ObjectNotFoundException {
		Company company = findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
				.getMessage(ExceptionMessageSource.COMPANY_NOT_FOUND)));
		company.setIsEnable(isEnable);
		saveAndFlush(company);
	}

}
