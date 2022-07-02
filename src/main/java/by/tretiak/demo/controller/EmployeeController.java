package by.tretiak.demo.controller;

import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.AddingCardRequest;
import by.tretiak.demo.model.pojo.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
@NoArgsConstructor
@Setter
public class EmployeeController extends AbstractController{

	@Autowired
	private EmployeeService service;

	private static final String EMPLOYEE_ID_PARAM = "employeeId";

	private static final String NEW_EMPLOYEE_PATH = "/companies/newemployee";

	private static final String ADD_CARD_PATH = "/addcard";

	@PostMapping(path = NEW_PATH)
	public void addNewEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(NEW_EMPLOYEE_PATH).forward(request,response);
		} catch (ServletException | IOException e) {
			response.setStatus(500);
		}
	}

	@PostMapping(path = ADD_CARD_PATH)
	public ResponseEntity<?> addNewCard(@RequestBody AddingCardRequest request) {
		return this.service.addNewEmployeeCard(request);
	}

	@PatchMapping(path = ID_PATH)
	public ResponseEntity<?> changePermission(@PathVariable(name = ID_PARAM) int id, @RequestParam(name = STATUS_PARAM) boolean isEnable) {
		return this.service.updateStatus(id, isEnable);
	}

}
