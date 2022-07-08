package by.tretiak.demo.repository;

import by.tretiak.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String userName);

    boolean existsByUsername(String username);

}
