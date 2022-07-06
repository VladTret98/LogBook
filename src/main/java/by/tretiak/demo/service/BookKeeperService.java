package by.tretiak.demo.service;

import java.util.List;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.pojo.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.repository.BookKeeperRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@Setter
public class BookKeeperService {

	@Autowired
	private BookKeeperRepository repository;

	@Autowired
	private ObjectMapper mapper;

	public String getAll() {
		try {
			List<BookKeeper> bookKeepers = this.repository.findAll();
			return mapper.writeValueAsString(bookKeepers);
		} catch (JsonProcessingException e) {
			return ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR);
		}
	}

	public ResponseEntity<?> setStatus(int userId, boolean status) {
		try {
			BookKeeper keeper = this.repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			keeper.setEnable(status);
			repository.save(keeper);
			return ResponseEntity.ok(new MessageResponse(MessageResponse.SUCCESS));
		} catch (ObjectNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public String getById(int id) {
		try {
			BookKeeper bookKeeper = this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			return this.mapper.writeValueAsString(bookKeeper);
		} catch (ObjectNotFoundException e) {
			return ExceptionMessageSource.getMessage(ExceptionMessageSource.USER_NOT_FOUND);
		} catch (JsonProcessingException e) {
			return ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR);
		}
	}

}
