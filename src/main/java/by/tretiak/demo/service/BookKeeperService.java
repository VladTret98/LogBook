package by.tretiak.demo.service;

import java.util.List;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.repository.BookKeeperRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
@Setter
public class BookKeeperService {

	@Autowired
	private BookKeeperRepository repository;

	public List<BookKeeper> getAll() {
			return this.repository.findAll();
	}

	@Transactional
	public BookKeeper setStatus(int userId, boolean status) throws ObjectNotFoundException {
		try {
			BookKeeper keeper = this.repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
					.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			keeper.setEnable(status);
			return repository.save(keeper);
		} catch (ObjectNotFoundException e) {
			throw e;
		}
	}

	public BookKeeper getById(int id) throws ObjectNotFoundException {
			try {
				return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
						.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
			} catch (ObjectNotFoundException e) {
				throw e;
			}
		}
}
