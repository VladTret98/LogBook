package by.tretiak.demo.controller;

import javax.validation.constraints.Min;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.BookKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.tretiak.demo.service.BookKeeperService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/keepers")
@NoArgsConstructor
@Setter
public class BookKeeperController extends AbstractController{

	@Autowired
	private BookKeeperService service;

	private static final String COMPANY_ID_PARAM = "companyId";

	@GetMapping()
	public List<BookKeeper> getKeepers() {
		return this.service.getAll();
	}

	@GetMapping(value = ID_PATH)
	public BookKeeper getKeeperById(@PathVariable(name = ID_PARAM) int id) throws ObjectNotFoundException {
		return this.service.getById(id);
	}

	@PostMapping(value = NEW_PATH)
	public MessageResponse addKeeper(@RequestBody BookKeeper bookKeeper,
									 @RequestParam(name = COMPANY_ID_PARAM) @Min(1) int companyId, BindingResult bindingResult)
			throws NotInputException, ObjectNotFoundException, ValidationException {
		validate(bindingResult);
		return this.service.addKeeper(bookKeeper, companyId);
	}

	@PatchMapping(value = ID_PATH)
	public BookKeeper changeStatus(@PathVariable(name = ID_PARAM) int id, @RequestParam(name = STATUS_PARAM) boolean status)
			throws ObjectNotFoundException {
		return this.service.setStatus(id, status);
	}

}
