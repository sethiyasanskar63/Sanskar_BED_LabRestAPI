package com.gl.studentmanagement.util;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gl.studentmanagement.entity.Role;
import com.gl.studentmanagement.entity.User;
import com.gl.studentmanagement.repository.RoleRepository;
import com.gl.studentmanagement.repository.UserRepository;

@Configuration
public class BootStrapAppData {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void insertAllData(ApplicationReadyEvent event) {
		User test1 = new User("test1", passwordEncoder.encode("welcome"));
		User test2 = new User("test2", passwordEncoder.encode("welcome"));

		Role userRole = new Role("ROLE_USER");
		Role adminRole = new Role("ROLE_ADMIN");

		roleRepository.saveAndFlush(userRole);
		roleRepository.saveAndFlush(adminRole);

		test1.addRole(adminRole);
		test1.addRole(userRole);
		test2.addRole(userRole);

		userRepository.saveAndFlush(test1);
		userRepository.saveAndFlush(test2);

	}
}