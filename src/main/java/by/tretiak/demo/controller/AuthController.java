package by.tretiak.demo.controller;

import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.pojo.LoginRequest;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.ERole;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.model.user.Role;
import by.tretiak.demo.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractController{

    private static final String SIGN_IN_PATH = "/signin";
    private static final String UPDATE_PASSWORD_PATH = "/updatepassword";

    @Autowired
    AuthService authService;

    @PostMapping(SIGN_IN_PATH)
    public MessageResponse authUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws ValidationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Employee employee = new Employee();
            employee.setUsername("employee");
            employee.setPassword("employee");
            employee.setEnable(true);
            employee.setRole(new Role(ERole.ROLE_USER));
            System.out.println(mapper.writeValueAsString(employee));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        validate(bindingResult);
        return this.authService.authUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping(UPDATE_PASSWORD_PATH)
    public MessageResponse updatePassword(@RequestBody String password, BindingResult bindingResult) throws ValidationException {
        validate(bindingResult);
        return this.authService.updatePassword(password);
    }

}
