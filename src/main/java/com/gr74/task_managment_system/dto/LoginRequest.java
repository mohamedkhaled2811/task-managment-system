package com.gr74.task_managment_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
		@Email @NotBlank String email,
		@NotBlank String password
) {}
