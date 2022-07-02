package by.tretiak.demo.repository;

import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tretiak.demo.model.user.BookKeeper;

@Repository
public interface BookKeeperRepository extends JpaRepository<BookKeeper, Integer> {



	default void setStatus(Integer id, Boolean isEnable) throws ObjectNotFoundException {
		BookKeeper keeper = findById(id).orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
				.getMessage(ExceptionMessageSource.USER_NOT_FOUND)));
		keeper.setEnable(isEnable);
		saveAndFlush(keeper);
	}

}
