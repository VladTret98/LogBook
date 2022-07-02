package by.tretiak.demo.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ValidationError {

    private String fieldName;

    private String errorMessage;

    public ValidationError(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

}
