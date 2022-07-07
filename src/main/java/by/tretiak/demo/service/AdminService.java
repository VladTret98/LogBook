package by.tretiak.demo.service;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.model.pojo.UserSignupRequest;
import by.tretiak.demo.model.user.Admin;
import by.tretiak.demo.model.user.Role;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@NoArgsConstructor
@Setter
public class AdminService {

    @Autowired
    private AuthService authService;

    public Admin addAdmin(UserSignupRequest request) throws NotInputException, ObjectNotFoundException {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());
        admin.setRoles(new HashSet<>());
        request.getRoles().stream().forEach(stringRole -> admin.getRoles().add(new Role(stringRole)));
        return (Admin) this.authService.registerUser(admin);
    }


}
