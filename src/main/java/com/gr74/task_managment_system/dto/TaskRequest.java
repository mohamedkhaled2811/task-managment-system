package com.gr74.task_managment_system.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
		@NotBlank String title,
		String description,
		@NotBlank String status
) {}
