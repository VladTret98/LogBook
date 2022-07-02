package by.tretiak.demo.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserSignupRequest {

    @Size(min = 5, max = 15, message = "{user.name.incorrect}")
    @NotNull(message = "{user.name.null}")
    private String username;

    @Size(min = 5, max = 15, message = "{user.password.incorrect}")
    @NotNull(message = "{user.password.null}")
    private String password;

    @NotNull(message = "{user.role.null}")
    private Set<String> roles;

}
