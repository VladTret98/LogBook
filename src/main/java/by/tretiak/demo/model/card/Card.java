package by.tretiak.demo.model.card;

import by.tretiak.demo.model.user.Employee;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isReady;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate validDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employees;

    @NotNull
    private String issuePoint;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    public Card(boolean isReady, LocalDate validDate, String issuePoint, String status) {
        this.isReady = isReady;
        this.validDate = validDate;
        this.issuePoint = issuePoint;
        this.status = CardStatus.valueOf(status);
    }

    public enum CardStatus {
        PERSONAL("PERSONAL"),
        CORPORATE("CORPORATE");

        private String value;

        CardStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
