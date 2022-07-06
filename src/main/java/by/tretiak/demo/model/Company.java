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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(employees, company.employees) && Objects.equals(keepers, company.keepers) && Objects.equals(isEnable, company.isEnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employees, keepers, isEnable);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", keepers=" + keepers +
                ", isEnable=" + isEnable +
                '}';
    }
}
