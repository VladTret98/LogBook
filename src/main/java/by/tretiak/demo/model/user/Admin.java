package by.tretiak.demo.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "admins")
@NoArgsConstructor
@Setter
@Getter
public class Admin extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Admin(String username, String password) {
        super(username, password);
    }

}
