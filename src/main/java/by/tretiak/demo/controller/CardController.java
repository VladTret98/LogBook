package by.tretiak.demo.controller;


import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.FullCardInfoDto;
import by.tretiak.demo.model.pojo.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.tretiak.demo.service.CardService;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping(path = "/cards")
@NoArgsConstructor
@Setter
public class CardController extends AbstractController{

	@Autowired
	private CardService service;

	private static final String ADD_CARD_PATH =  "/employees/addcard";
	private static final String BALANCE_PATH = "/balance";

	@GetMapping
	public List<FullCardInfoDto> getCards() throws JsonProcessingException {
		return this.service.getCards();
	}

	@PostMapping(path = NEW_PATH)
	public void orderNewCard(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(ADD_CARD_PATH).forward(request, response);
		} catch (ServletException | IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public MessageResponse addEmployee(@RequestParam int cardId,
									   @RequestParam int employeeId) throws ObjectNotFoundException {
		return this.service.addEmployeeToCard(cardId, employeeId);
	}
	@GetMapping(BALANCE_PATH)
	public MessageResponse getCardBalance(@RequestParam int cardId, HttpServletResponse response)  {
			return new MessageResponse(String.valueOf(this.service.getCardBalance(cardId)));
	}

}
