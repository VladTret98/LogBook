package by.tretiak.demo.service;

import by.tretiak.demo.model.pojo.UserSignupRequest;
import by.tretiak.demo.model.user.Admin;
import by.tretiak.demo.model.user.Role;
import by.tretiak.demo.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@NoArgsConstructor
@Setter
public class AdminService {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthService authService;

    public ResponseEntity addAdmin(UserSignupRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());
        admin.setRoles(new HashSet<Role>());
        request.getRoles().stream().forEach(stringRole -> admin.getRoles().add(new Role(stringRole)));
            return this.authService.registerUser(admin);
    }


}
