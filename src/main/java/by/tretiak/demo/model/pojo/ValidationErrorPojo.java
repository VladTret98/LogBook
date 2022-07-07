package by.tretiak.demo.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ValidationErrorPojo {

    private String fieldName;

    private String errorMessage;

    public ValidationErrorPojo(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

}
