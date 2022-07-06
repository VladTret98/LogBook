package by.tretiak.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tretiak.demo.model.user.BookKeeper;

@Repository
public interface BookKeeperRepository extends JpaRepository<BookKeeper, Integer> {

}
