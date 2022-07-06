package by.tretiak.demo.repository;

import by.tretiak.demo.model.user.Admin;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin getById(Integer id);

}
