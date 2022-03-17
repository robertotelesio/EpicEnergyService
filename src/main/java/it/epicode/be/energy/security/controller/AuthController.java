package it.epicode.be.energy.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import it.epicode.be.energy.security.exceptions.EpicEnergyException;
import it.epicode.be.energy.security.model.LoginRequest;
import it.epicode.be.energy.security.model.LoginResponse;
import it.epicode.be.energy.security.model.RequestRegisterUser;
import it.epicode.be.energy.security.model.Role;
import it.epicode.be.energy.security.model.Roles;
import it.epicode.be.energy.security.model.User;
import it.epicode.be.energy.security.repository.RoleRepository;
import it.epicode.be.energy.security.repository.UserRepository;
import it.epicode.be.energy.security.service.UserDetailsImpl;
import it.epicode.be.energy.security.util.JwtUtils;



@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		LoginResponse loginResponse = new LoginResponse();

		loginResponse.setToken(token);
		loginResponse.setRoles(roles);

		return ResponseEntity.ok(loginResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody RequestRegisterUser registerUser) {
		if (userRepository.existsByEmail(registerUser.getEmail())) {
			return new ResponseEntity<String>("Errore email già in uso!", HttpStatus.BAD_REQUEST);
		} else if (userRepository.existsByUserName(registerUser.getUserName())) {
			return new ResponseEntity<String>("Errore username già in uso!", HttpStatus.BAD_REQUEST);
		}

		User userReg = new User();
		userReg.setUserName(registerUser.getUserName());
		userReg.setEmail(registerUser.getEmail());
		userReg.setPassword(encoder.encode(registerUser.getPassword()));
		userReg.setNome(registerUser.getNome());
		userReg.setCognome(registerUser.getCognome());

		if (registerUser.getRoles().isEmpty()) {
			Optional<Role> ruolo = roleRepository.findByRoleName(Roles.ROLE_USER);
			Set<Role> ruoli = new HashSet<>();
			ruoli.add(ruolo.get());
			userReg.setRoles(ruoli);
		} else {
			Set<Role> ruoli = new HashSet<>();
			for (String s : registerUser.getRoles()) {
				switch (s.toUpperCase()) {
				case "ADMIN":
					Optional<Role> ruoloA = roleRepository.findByRoleName(Roles.ROLE_ADMIN);
					ruoli.add(ruoloA.get());
					break;
				case "USER":
					Optional<Role> ruoloU = roleRepository.findByRoleName(Roles.ROLE_USER);
					ruoli.add(ruoloU.get());
					break;
				default:
					throw new EpicEnergyException("ruolo non trovato");

				}

			}
			userReg.setRoles(ruoli);
		}
		userRepository.save(userReg);
		return new ResponseEntity<>("Utente inserito con successo: " + userReg.toString(), HttpStatus.CREATED);
	}
}