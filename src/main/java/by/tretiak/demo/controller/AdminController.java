package by.tretiak.demo.controller;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.user.Admin;
import by.tretiak.demo.service.AdminService;
import by.tretiak.demo.service.CardService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/admins")
@NoArgsConstructor
@Setter
public class AdminController extends AbstractController{

    @Autowired
    private AdminService service;

    @PostMapping(path = NEW_PATH)
    public Admin addAdmin(@Valid @RequestBody Admin admin, BindingResult bindingResult)
            throws NotInputException, ObjectNotFoundException, ValidationException {
        validate(bindingResult);
        return this.service.addAdmin(admin);
    }

}
