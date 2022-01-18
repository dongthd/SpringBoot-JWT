package vn.security.userprincal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPrinciple implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;
	private String phone;
	private String address;
	private Boolean gender;
	private Boolean status;
	private String avatar;
	private LocalDate registerDate;
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> roles;

	public UserPrinciple(Long id, String username, String email, String password, String phone, String address,
			Boolean gender, Boolean status, String avatar, LocalDate registerDate,
			Collection<? extends GrantedAuthority> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.status = status;
		this.avatar = avatar;
		this.registerDate = registerDate;
		this.roles = roles;
	}

	public static UserPrinciple build(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()
				.name())).collect(Collectors.toList());

		return new UserPrinciple(
				user.getId(), 
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				user.getPhone(),
				user.getAddress(),
				user.getGender(),
				user.getStatus(),
				user.getAvatar(),
				user.getRegisterDate(),
				authorities
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserPrinciple user = (UserPrinciple) object;
		return Objects.equals(id, user.id);

	}

//	@Override
//	public String getUsername() {
//		return getEmail();
//	}

}
