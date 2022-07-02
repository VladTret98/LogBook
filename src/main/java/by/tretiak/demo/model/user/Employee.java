package by.tretiak.demo.model.user;

import by.tretiak.demo.model.Company;
import by.tretiak.demo.model.card.Card;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "employees")
public class Employee extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
	private Card personalCard;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
	private List<Card> otherCards;

	@ManyToOne
	@JoinColumn(name = "company_id")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
	private Company company;

	@Column(name = "is_enable", nullable = false)
	private boolean isEnable;

	public Employee( String username, String password) {
		super(username, password);
	}

}
