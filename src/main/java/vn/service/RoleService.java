package vn.service;

import java.util.Optional;

import vn.entity.Role;
import vn.entity.RoleName;

public interface RoleService {

	Optional<Role> findByName(RoleName name);
}
