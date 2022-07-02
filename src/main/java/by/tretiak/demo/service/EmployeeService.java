package by.tretiak.demo.service;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.AddingCardRequest;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.service.security.UserDetailsImpl;
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
import java.util.List;

@Service
@NoArgsConstructor
@Setter
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;


	public ResponseEntity<?> addNewEmployeeCard(AddingCardRequest request) {
		Card card = new Card(request.isReady(), request.getValidDate(), request.getIssuePoint(), request.getStatus());
		try {
			if (card.getStatus().equals(Card.CardStatus.CORPORATE)) {
				return addCorporateCard(card, request.getEmployeesId());
			} else if (card.getStatus().equals(Card.CardStatus.PERSONAL) && request.getEmployeesId().size() > 1) {
				return ResponseEntity.badRequest().body(new MessageResponse(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.BAD_REQUEST)));
			}
			else {
				return addPersonalCard(card, request.getEmployeesId().get(0));
			}
		} catch (ObjectNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	ResponseEntity<Card> addPersonalCard(Card card, Integer employeeId) throws ObjectNotFoundException {
		Employee employee = this.repository.findById(employeeId).orElseThrow(() ->
				new ObjectNotFoundException(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
		card.setEmployees(new ArrayList<>(1));
		card.getEmployees().add(employee);
		employee.setPersonalCard(card);
		return ResponseEntity.ok(this.repository.save(employee).getPersonalCard());
	}

	@Transactional
	ResponseEntity<Card> addCorporateCard(Card card, List<Integer> employeesId) throws ObjectNotFoundException {
		for (Integer id: employeesId) {
			Employee employee = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			card.setEmployees(new ArrayList<>());
			card.getEmployees().add(employee);
			employee.getOtherCards().add(card);
			employee = this.repository.save(employee);
		}
		return ResponseEntity.ok(card);
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
