package vn.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private String password;
	private String phone;
	private String address;
	private Boolean gender;
	private String avatar;
	@Column(name = "register_date")
	private LocalDate registerDate;
	private Boolean status;
	private String token;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String encode, String phone, String address, Boolean gender,
			Boolean status, String avatar, LocalDate registerDate) {
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.status = status;
		this.avatar = avatar;
		this.registerDate = registerDate;
		this.password = encode;

	}
}
