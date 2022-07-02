package by.tretiak.demo.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class JwtResponse {

    private String token;

    private int id;

    private String username;

    private List<String> roles;

    public JwtResponse(String token, int id, String username, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

}
