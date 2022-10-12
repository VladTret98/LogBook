package by.tretiak.demo.service;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.pojo.CardDto;
import by.tretiak.demo.model.pojo.CardsDto;
import by.tretiak.demo.model.pojo.FullCardInfoDto;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import by.tretiak.demo.repository.CardRepository;
import lombok.Setter;

import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@NoArgsConstructor
@Setter
public class CardService {

	@Autowired
	private CardRepository repository;

	private static final String GET_ALL_CARDS = "http://localhost:8082/CardService_war_exploded/cards";
	private static final String CARD_BY_ID = "http://localhost:8082/CardService_war_exploded/cards";
	private static final String GET_BALANCE_BY_ID = "http://localhost:8082/CardService_war_exploded/balance";


	public List<FullCardInfoDto> getCards() throws JsonProcessingException {
			List<Card> cards = this.repository.findAll();
			RestTemplate template = new RestTemplate();
			String cardsString = template.getForObject(GET_ALL_CARDS, String.class);
			ObjectMapper mapper = new ObjectMapper();
			CardsDto cardsDto = mapper.readValue(cardsString, CardsDto.class);
			List<FullCardInfoDto> finalList = new ArrayList<>();
		for (int i = 0; i < cardsDto.getCards().size(); i++) {
			for (Card card: cards) {
				if (card.getId() == cardsDto.getCards().get(i).getId()) {
					finalList.add(new FullCardInfoDto(card, cardsDto.getCards().get(i)));
				}
			}
		}
			return finalList;
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

	public Double getCardBalance(int cardId) {
		RestTemplate template = new RestTemplate();
		CardDto dto = new CardDto();
		dto.setId(cardId);
		HttpEntity<CardDto> requestBody = new HttpEntity<>(dto);
		CardDto card = template.postForObject(GET_BALANCE_BY_ID, requestBody, CardDto.class);
		return card.getBalance();
	}



}
