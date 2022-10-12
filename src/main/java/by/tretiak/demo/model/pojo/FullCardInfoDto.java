package by.tretiak.demo.model.pojo;

import by.tretiak.demo.model.card.Card;
import by.tretiak.demo.model.user.Employee;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class FullCardInfoDto {

    private int id;

    private boolean isReady;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate validDate;

    private List<Employee> employees;

    private String issuePoint;

    private Card.CardStatus status;

    private Double balance;

    public FullCardInfoDto(Card card, CardDto cardDto) {
        this.id = card.getId();
        this.employees = card.getEmployees();
        this.isReady = card.isReady();
        this.issuePoint = card.getIssuePoint();
        this.status = card.getStatus();
        this.validDate = card.getValidDate();
        this.balance = cardDto.getBalance();
    }

}
