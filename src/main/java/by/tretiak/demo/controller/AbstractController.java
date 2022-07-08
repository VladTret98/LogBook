package by.tretiak.demo.controller;


import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.pojo.ValidationErrorPojo;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class AbstractController {


    static final String ID_PATH = "/{id}";

    static final String ID_PARAM = "id";

    static final String NEW_PATH = "/new";

    static final String STATUS_PARAM = "status";

    protected Boolean validate(BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            List<ValidationErrorPojo> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(new ValidationErrorPojo(error.getField(), error.getDefaultMessage())));
            throw new ValidationException(errors);
        }
        return true;
    }

}
