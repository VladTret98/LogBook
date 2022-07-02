package by.tretiak.demo.service;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.service.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import by.tretiak.demo.repository.EmployeeRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@NoArgsConstructor
@Setter
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private ObjectMapper mapper;

	public String addEmployee(String userName, String password, String companyId) {
		try {
			Employee employee = new Employee();
			employee.setUsername(userName);
			employee.setPassword(password);
			Company company = new Company();
			company.setId(Integer.parseInt(companyId));
			employee.setCompany(company);
			return this.mapper.writeValueAsString(this.repository.save(employee));
		} catch (JsonProcessingException e) {
			return ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> addNewEmployeeCard(int employeeId, Card card) {
		try {
			if (card.getStatus().equals(Card.CardStatus.CORPORATE)) {
				// TO DO!!!!!
				return null;
			} else {
				Employee employee = this.repository.findById(employeeId).orElseThrow(() ->
						new ObjectNotFoundException(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
				card.setEmployees(new ArrayList<>(1));
				card.getEmployees().add(employee);
				employee.setPersonalCard(card);
				card =  this.repository.save(employee).getPersonalCard();
				return ResponseEntity.ok(card);
			}
		} catch (ObjectNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> updateStatus(int userId, boolean isEnable) {
		UserDetailsImpl bossUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			Employee employee = this.repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			if (bossUserDetails.getCompanyId().equals(employee.getCompany().getId())) {
				employee.setEnable(isEnable);
			} else {
				return new ResponseEntity<>(ExceptionMessageSource.getMessage(ExceptionMessageSource.NOT_ACCESS), HttpStatus.FORBIDDEN);
			}
		} catch (ObjectNotFoundException e) {
			return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(MessageResponse.SUCCESS);
	}

}
