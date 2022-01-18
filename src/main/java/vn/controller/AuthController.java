package vn.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.dto.request.SignInForm;
import vn.dto.request.SignUpForm;
import vn.dto.response.JwtResponse;
import vn.dto.response.ResponseMessage;
import vn.entity.Role;
import vn.entity.RoleName;
import vn.entity.User;
import vn.security.jwt.JwtProvider;
import vn.security.userprincal.UserPrinciple;
import vn.service.impl.RoleServiceImpl;
import vn.service.impl.UserServiceImpl;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	RoleServiceImpl roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {

		if (userService.existsByUsername(signUpForm.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("The username existed! Please try again!"), HttpStatus.OK);
		}

		if (userService.existsByEmail(signUpForm.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("The email existed! Please try again!"), HttpStatus.OK);
		}

		// create new user
		User user = new User(signUpForm.getUsername(), signUpForm.getEmail(),
				passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getPhone(), signUpForm.getAddress(),
				signUpForm.getGender(), signUpForm.getStatus(), signUpForm.getAvatar(), signUpForm.getRegisterDate());

		Set<Role> roles = new HashSet<>();
		Role userRole = roleService.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Role not found"));
		roles.add(userRole);

		user.setRoles(roles);
		userService.save(user);
		return new ResponseEntity<>(new ResponseMessage("Create user success!"), HttpStatus.OK);

	}

	@PostMapping("/signin")
	public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getEmail(), signInForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.createToken(authentication);
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(token, userPrincipal.getId(), userPrincipal.getUsername(),
				userPrincipal.getEmail(), userPrincipal.getPassword(), userPrincipal.getPhone(),
				userPrincipal.getAddress(), userPrincipal.getGender(), userPrincipal.getStatus(),
				userPrincipal.getAvatar(), userPrincipal.getRegisterDate(), userPrincipal.getAuthorities()));
	}

}
