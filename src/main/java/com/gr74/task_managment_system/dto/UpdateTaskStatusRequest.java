package com.gr74.task_managment_system.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskStatusRequest(@NotBlank String status) {}
