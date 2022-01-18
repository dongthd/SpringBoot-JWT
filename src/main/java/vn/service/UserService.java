package vn.service;

import java.util.Optional;

import vn.entity.User;

public interface UserService {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User save(User user);

}
