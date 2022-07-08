package by.tretiak.demo.service;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.model.user.Admin;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Setter
public class AdminService {

    @Autowired
    private AuthService authService;

    public Admin addAdmin(Admin admin) throws NotInputException, ObjectNotFoundException {
        return (Admin) this.authService.registerUser(admin);
    }


}
