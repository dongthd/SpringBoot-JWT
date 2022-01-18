package vn.dto.request;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

	private String username;
	private String email;
	private String password;
	private String phone;
	private String address;
	private Boolean gender;
	private Boolean status;
	private String avatar;
	private LocalDate registerDate;

	private Set<String> roles;

}
