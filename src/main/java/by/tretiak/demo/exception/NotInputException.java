package by.tretiak.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotInputException extends Exception{

    public NotInputException(String message) {
        super(message);
    }

}
