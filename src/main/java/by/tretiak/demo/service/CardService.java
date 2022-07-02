package by.tretiak.demo.service;

import by.tretiak.demo.exception.source.ExceptionMessageSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.tretiak.demo.repository.CardRepository;
import lombok.Setter;

import lombok.NoArgsConstructor;


@Service
@NoArgsConstructor
@Setter
public class CardService {

	@Autowired
	private CardRepository repository;

	@Autowired
	private ObjectMapper mapper;

	public String getCards() {
		try {
			return this.mapper.writeValueAsString(this.repository.findAll());
		} catch (JsonProcessingException e) {
			return ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR);
		}

	}

}
