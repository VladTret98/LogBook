package by.tretiak.demo.controller;

import by.tretiak.demo.model.pojo.UserSignupRequest;
import by.tretiak.demo.model.pojo.ValidationError;
import by.tretiak.demo.service.AdminService;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@RequestMapping(path = "/admins")
@NoArgsConstructor
@Setter
public class AdminController extends AbstractController{

    @Autowired
    private AdminService service;

    @PostMapping(path = NEW_PATH)
    public ResponseEntity addAdmin(@Valid @RequestBody UserSignupRequest signupRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errors);
        }
        return this.service.addAdmin(signupRequest);
    }

}
