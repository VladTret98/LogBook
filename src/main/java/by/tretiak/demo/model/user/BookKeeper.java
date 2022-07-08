package by.tretiak.demo.model.user;

import by.tretiak.demo.model.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "keepers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookKeeper extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_enable", nullable = false)
    private boolean isEnable;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @Cascade(CascadeType.PERSIST)
    private Company company;

    public BookKeeper(String username, String password) {
        super(username, password);
    }

}
