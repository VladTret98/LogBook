package by.tretiak.demo.repository;

import by.tretiak.demo.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	public Admin getById(Integer id);

}
