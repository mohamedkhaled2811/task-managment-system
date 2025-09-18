package com.gr74.task_managment_system.dto;

public record TaskResponse(
		Long id,
		String title,
		String description,
		String status
) {}
