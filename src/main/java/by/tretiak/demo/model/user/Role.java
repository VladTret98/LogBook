package by.tretiak.demo.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private ERole value;

    public Role(ERole role) {
        this.value = role;
    }

    public Role(String role) {
        if (role.equals("ROLE_ADMIN")) {
            this.value = ERole.ROLE_ADMIN;
        } else if (role.equals("ROLE_USER")) {
            this.value = ERole.ROLE_USER;
        } else if (role.equals("ROLE_KEEPER")) {
            this.value = ERole.ROLE_KEEPER;
        }
    }

}
