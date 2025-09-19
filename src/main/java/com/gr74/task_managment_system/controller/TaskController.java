package com.gr74.task_managment_system.controller;

import com.gr74.task_managment_system.dto.TaskRequest;
import com.gr74.task_managment_system.dto.TaskResponse;
import com.gr74.task_managment_system.dto.UpdateTaskStatusRequest;
import com.gr74.task_managment_system.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;


	@PostMapping
	public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request,
	                                           @AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(taskService.create(request, user.getUsername()));
	}

	@GetMapping
	public ResponseEntity<List<TaskResponse>> findAll(@AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(taskService.findAllForUser(user.getUsername()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id,
	                                                 @Valid @RequestBody UpdateTaskStatusRequest request,
	                                                 @AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(taskService.updateStatus(id, request.status(), user.getUsername()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
		taskService.delete(id, user.getUsername());
		return ResponseEntity.noContent().build();
	}
}