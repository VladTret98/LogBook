package by.tretiak.demo.model.user;

import lombok.Getter;

@Getter
public enum ERole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_KEEPER("ROLE_KEEPER");

    private String value;

    ERole(String value) {
        this.value = value;
    }

}
