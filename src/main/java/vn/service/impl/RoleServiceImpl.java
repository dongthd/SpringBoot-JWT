package vn.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.entity.Role;
import vn.entity.RoleName;
import vn.repository.RoleRepository;
import vn.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<Role> findByName(RoleName name) {
		return roleRepository.findByName(name);
	}

}
