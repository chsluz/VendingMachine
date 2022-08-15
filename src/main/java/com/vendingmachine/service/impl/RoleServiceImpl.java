package com.vendingmachine.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingmachine.entity.Role;
import com.vendingmachine.repository.RoleRepository;
import com.vendingmachine.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	@Override
	public Role save(Role role) {
		log.info("Save role to the database");
		return roleRepository.save(role);
	}
	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

}
