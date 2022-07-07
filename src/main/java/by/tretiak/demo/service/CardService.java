package by.tretiak.demo.service;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.tretiak.demo.repository.CardRepository;
import lombok.Setter;

import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@NoArgsConstructor
@Setter
public class CardService {

	@Autowired
	private CardRepository repository;

	public List<Card> getCards() {
			return this.repository.findAll();
	}

	@Transactional
	public MessageResponse addEmployeeToCard(int cardId, int employeeId) throws ObjectNotFoundException {
		try {
			Card card = this.repository.findById(cardId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.DATA_NOT_FOUND)));
			if (card.getStatus().equals(Card.CardStatus.CORPORATE)) {
				Employee employee = new Employee();
				employee.setId(employeeId);
				card.getEmployees().add(employee);
				this.repository.save(card);
				return new MessageResponse(MessageResponse.SUCCESS);
			} else {
				throw new RuntimeException(ExceptionMessageSource.getMessage(ExceptionMessageSource.BAD_REQUEST));
			}
		} catch (ObjectNotFoundException e) {
			throw e;
		}
	}
}
