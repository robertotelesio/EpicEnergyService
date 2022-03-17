package it.epicode.be.energy.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.security.model.User;
import it.epicode.be.energy.security.repository.UserRepository;




@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

}
