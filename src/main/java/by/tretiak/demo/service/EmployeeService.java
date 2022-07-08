package by.tretiak.demo.service;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.AddingCardRequest;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.repository.CompanyWorkersRepository;
import by.tretiak.demo.service.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import by.tretiak.demo.repository.EmployeeRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Setter
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private CompanyWorkersRepository companyWorkersRepository;

	@Autowired
	private AuthService authService;

	public MessageResponse addNewEmployeeCard(AddingCardRequest request) throws ObjectNotFoundException {
		Card card = new Card(request.isReady(), request.getValidDate(), request.getIssuePoint(), request.getStatus());
			if (card.getStatus().equals(Card.CardStatus.CORPORATE)) {
				return addCorporateCard(card, request.getEmployeesId());
			} else if (card.getStatus().equals(Card.CardStatus.PERSONAL) && request.getEmployeesId().size() > 1) {
				throw new RuntimeException(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.BAD_REQUEST));
			}
			else {
				return addPersonalCard(card, request.getEmployeesId().get(0));
			}
	}

	@Transactional
	MessageResponse addPersonalCard(Card card, Integer employeeId) throws ObjectNotFoundException {
		Employee employee = this.repository.findById(employeeId).orElseThrow(() ->
				new ObjectNotFoundException(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
		card.setEmployees(new ArrayList<>(1));
		card.getEmployees().add(employee);
		employee.setPersonalCard(card);
		return new MessageResponse(MessageResponse.SUCCESS);
	}

	@Transactional
	MessageResponse addCorporateCard(Card card, List<Integer> employeesId) throws ObjectNotFoundException {
		for (Integer id: employeesId) {
			Employee employee = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			card.setEmployees(new ArrayList<>());
			card.getEmployees().add(employee);
			employee.getOtherCards().add(card);
			this.repository.save(employee);
		}
		return new MessageResponse(MessageResponse.SUCCESS);
	}

	public MessageResponse addEmployee(Employee employee, int companyId) throws NotInputException, ObjectNotFoundException {
		employee = (Employee) this.authService.prepareNewUser(employee);
		this.companyWorkersRepository.addEmployee(employee, companyId);
		return new MessageResponse(MessageResponse.USER_CREATED);
	}

	@Transactional
	public MessageResponse updateStatus(int userId, boolean isEnable) throws ObjectNotFoundException {
		UserDetailsImpl bossUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Employee employee = this.repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			if (bossUserDetails.getCompanyId().equals(employee.getCompany().getId())) {
				employee.setEnable(isEnable);
				this.repository.save(employee);
			} else {
				throw new AccessControlException(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.NOT_ACCESS));
			}
		return new MessageResponse(MessageResponse.SUCCESS);
	}

}
