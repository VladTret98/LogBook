package by.tretiak.demo.controller;

import by.tretiak.demo.model.pojo.LoginRequest;
import by.tretiak.demo.model.pojo.ValidationError;
import by.tretiak.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity authUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
        }
        return this.authService.authUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping(UPDATE_PASSWORD_PATH)
    public ResponseEntity updatePassword(@RequestBody String password, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
        }
            return this.authService.updatePassword(password);
    }

}
