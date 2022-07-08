package by.tretiak.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tretiak.demo.model.card.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

}
