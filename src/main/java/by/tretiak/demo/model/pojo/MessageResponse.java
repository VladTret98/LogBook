package by.tretiak.demo.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MessageResponse {

        public static final String USER_CREATED = "Пользователь создан";

        public static final String USERNAME_EXISTS = "Пользователь с таким именем уже существует";

        public static final String SUCCESS = "Успешно";

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

}
