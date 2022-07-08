package by.tretiak.demo.controller;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.pojo.AddingCardRequest;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.tretiak.demo.service.EmployeeService;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping(path = "/employees")
@NoArgsConstructor
@Setter
public class EmployeeController extends AbstractController{

	@Autowired
	private EmployeeService service;

	private static final String ADD_CARD_PATH = "/addcard";

	private static final String COMPANY_ID_PARAM = "companyId";

	@PostMapping(path = NEW_PATH)
	public MessageResponse addNewEmployee(@Valid @RequestBody Employee employee,
										  @RequestParam(name = COMPANY_ID_PARAM) @Min(1) int companyId, BindingResult bindingResult)
			throws ObjectNotFoundException, NotInputException, ValidationException {
		validate(bindingResult);
		return this.service.addEmployee(employee, companyId);
	}

	@PostMapping(path = ADD_CARD_PATH)
	public MessageResponse addNewCard(@RequestBody AddingCardRequest request, BindingResult bindingResult) throws ValidationException, ObjectNotFoundException {
		validate(bindingResult);
		return this.service.addNewEmployeeCard(request);
	}

	@PatchMapping(path = ID_PATH)
	public MessageResponse changePermission(@PathVariable(name = ID_PARAM) int id, @RequestParam(name = STATUS_PARAM) boolean isEnable) throws ObjectNotFoundException {
		return this.service.updateStatus(id, isEnable);
	}

}
