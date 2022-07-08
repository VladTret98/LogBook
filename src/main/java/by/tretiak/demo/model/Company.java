package by.tretiak.demo.model;

import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 4, max = 25)
    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<BookKeeper> keepers;

    private Boolean isEnable;

}
