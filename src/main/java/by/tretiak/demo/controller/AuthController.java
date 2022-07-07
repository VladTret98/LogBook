package by.tretiak.demo.controller;

import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.pojo.LoginRequest;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.pojo.ValidationErrorPojo;
import by.tretiak.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractController{

    private static final String SIGN_IN_PATH = "/signin";
    private static final String UPDATE_PASSWORD_PATH = "/updatepassword";

    @Autowired
    AuthService authService;

    @PostMapping(SIGN_IN_PATH)
    public MessageResponse authUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws ValidationException {
        validate(bindingResult);
        return this.authService.authUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping(UPDATE_PASSWORD_PATH)
    public MessageResponse updatePassword(@RequestBody String password, BindingResult bindingResult) throws ValidationException {
        validate(bindingResult);
        return this.authService.updatePassword(password);
    }

}
