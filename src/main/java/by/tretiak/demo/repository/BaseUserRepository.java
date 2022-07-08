package by.tretiak.demo.repository;

import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.user.Admin;
import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.ERole;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.model.user.Role;
import by.tretiak.demo.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Setter
@Getter
@NoArgsConstructor
public class BaseUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookKeeperRepository bookKeeperRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public User save(User user) {
        ERole role = user.getRole().getValue();
        if (role.equals(ERole.ROLE_ADMIN)) {
            Admin admin = new Admin(user.getUsername(), user.getPassword());
            admin.setRole(user.getRole());
            return this.adminRepository.save(admin);
        } else if (role.equals(ERole.ROLE_KEEPER)) {
            BookKeeper keeper = new BookKeeper(user.getUsername(), user.getPassword());
            keeper.setEnable(true);
            keeper.setRole(user.getRole());
            keeper.setCompany(((BookKeeper) user).getCompany());
            return this.bookKeeperRepository.save(keeper);
        } else {
            Employee employee = new Employee(user.getUsername(), user.getPassword());
            employee.setRole(user.getRole());
            return this.employeeRepository.save(employee);
        }
    }

    @Transactional
    public User findByUsername(String userName) throws UsernameNotFoundException {
        User user;
        try {
            user = this.userRepository.findByUsername(userName);
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.USER_NOT_FOUND));
        }
        Role userRole = user.getRole();
        if (userRole.getValue().equals(ERole.ROLE_ADMIN)) {
            return this.adminRepository.findById(user.getId()).get();
        } else if (userRole.getValue().equals(ERole.ROLE_KEEPER)) {
            return this.bookKeeperRepository.findById(user.getId()).get();
        } else {
            return this.employeeRepository.findById(user.getId()).get();
        }
    }

}
