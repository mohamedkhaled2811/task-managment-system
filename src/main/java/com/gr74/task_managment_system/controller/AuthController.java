package com.gr74.task_managment_system.controller;

import com.gr74.task_managment_system.dto.*;
import com.gr74.task_managment_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
		authService.register(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		AuthResponse token = authService.login(request);
		return ResponseEntity.ok(token);
	}


    
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader(name = "Authorization", required = false) String authHeader) {
		// Optional for JWT; provided per requirements
		authService.logout(authHeader != null ? authHeader.replace("Bearer ", "") : null);
		return ResponseEntity.ok().build();
	}
}
