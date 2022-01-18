package vn.dto.response;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private String password;
	private String phone;
	private String address;
	private Boolean gender;
	private Boolean status;
	private String avatar;
	private LocalDate registerDate;

	private Collection<? extends GrantedAuthority> roles;

	public JwtResponse(String token, Long id, String username, String email, String password, String phone,
			String address, Boolean gender, Boolean status, String avatar, LocalDate registerDate,
			Collection<? extends GrantedAuthority> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.registerDate = registerDate;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.status = status;
		this.avatar = avatar;
		this.roles = roles;
	}
}
