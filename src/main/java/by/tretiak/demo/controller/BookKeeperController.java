package by.tretiak.demo.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tretiak.demo.exception.source.ExceptionMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.tretiak.demo.service.BookKeeperService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/keepers")
@NoArgsConstructor
@Setter
public class BookKeeperController extends AbstractController{

	@Autowired
	private BookKeeperService service;

	private static final String NEW_KEEPER_PATH = "/companies/newkeeper";

	@GetMapping()
	public String getKeepers() {
		return this.service.getAll();
	}

	@GetMapping(value = ID_PATH)
	public String getKeeperById(@PathVariable(name = ID_PARAM) int id) {
		return this.service.getById(id);
	}

	@PostMapping(value = NEW_PATH)
	public void addKeeper(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher(NEW_KEEPER_PATH).forward(request,response);
		} catch (ServletException | IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR));
		}
	}

	@PatchMapping(value = ID_PATH)
	public ResponseEntity<?> changeStatus(@PathVariable(name = ID_PARAM) int id, @RequestParam(name = STATUS_PARAM) boolean status) {
		return this.service.setStatus(id, status);
	}

}
