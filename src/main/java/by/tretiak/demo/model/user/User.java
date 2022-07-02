package by.tretiak.demo.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 5, max = 15, message = "user.name.incorrect")
	@NotNull
	private String username;

	@NotNull
	private String password;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
	protected Set<Role> roles = new HashSet<>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
