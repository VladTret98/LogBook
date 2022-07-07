package by.tretiak.demo.exception;

import by.tretiak.demo.model.pojo.ValidationErrorPojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ValidationException extends Exception{

    private List<ValidationErrorPojo> errors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<ValidationErrorPojo> errors) {
        this.errors = errors;
    }

    public String getErrors() {
        return errors.toString();
    }
}
