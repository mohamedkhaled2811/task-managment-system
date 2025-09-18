package com.gr74.task_managment_system.dto;

import java.time.Instant;

public record ErrorResponse(String message, int status, Instant timestamp) {}
