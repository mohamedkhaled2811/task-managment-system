package com.gr74.task_managment_system.service;

import com.gr74.task_managment_system.dto.AuthResponse;
import com.gr74.task_managment_system.dto.LoginRequest;
import com.gr74.task_managment_system.dto.RegisterRequest;
import com.gr74.task_managment_system.model.User;
import com.gr74.task_managment_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.gr74.task_managment_system.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor	
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Transactional
	public void register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email already in use");
		}
		User user = User.builder()
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.name(request.name())
				.build();
		userRepository.save(user);
	}


	public AuthResponse login(LoginRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		} catch (AuthenticationException e) {
			throw new IllegalArgumentException("Invalid email or password");
		}
		String token = jwtService.generateToken(request.email());
		return new AuthResponse(token);
	}

	public void logout(String token) {
		// Stateless JWT: no server-side state. Could implement blacklist if needed.
	}	
}
