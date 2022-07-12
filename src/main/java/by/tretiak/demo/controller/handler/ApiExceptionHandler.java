package by.tretiak.demo.controller.handler;

import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.ValidationException;
import by.tretiak.demo.model.pojo.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.AccessControlException;

@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MessageResponse exception(Exception exception) {
        return new MessageResponse(exception.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageResponse validationException(ValidationException exception) {
        return new MessageResponse(exception.getErrors());
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageResponse objectNotFoundException(ObjectNotFoundException exception) {
        return new MessageResponse(exception.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageResponse runtimeException(RuntimeException exception) {
        return new MessageResponse(exception.getMessage());
    }

    @ExceptionHandler(value = NotInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageResponse notInputException(NotInputException exception) {
        return new MessageResponse(exception.getMessage());
    }

    @ExceptionHandler(value = AccessControlException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public MessageResponse accessControlException(AccessControlException exception) {
        return new MessageResponse(exception.getMessage());
    }

}
