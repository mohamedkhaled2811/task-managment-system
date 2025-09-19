package com.gr74.task_managment_system.service;

import com.gr74.task_managment_system.dto.TaskRequest;
import com.gr74.task_managment_system.dto.TaskResponse;
import com.gr74.task_managment_system.model.Task;
import com.gr74.task_managment_system.model.User;
import com.gr74.task_managment_system.repository.TaskRepository;
import com.gr74.task_managment_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService  {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;


	@Transactional
	public TaskResponse create(TaskRequest request, String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		Task task = Task.builder()
				.title(request.title())
				.description(request.description())
				.status(request.status())
				.user(user)
				.build();
		taskRepository.save(task);
		return toResponse(task);
	}


	public List<TaskResponse> findAllForUser(String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		return taskRepository.findByUser(user).stream().map(this::toResponse).toList();
	}


	@Transactional
	public TaskResponse updateStatus(Long id, String status, String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
		if (!task.getUser().getId().equals(user.getId())) {
			throw new SecurityException("Forbidden");
		}
		task.setStatus(status);
		return toResponse(task);
	}

	@Transactional
	public void delete(Long id, String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
		if (!task.getUser().getId().equals(user.getId())) {
			throw new SecurityException("Forbidden");
		}
		taskRepository.delete(task);
	}

	private TaskResponse toResponse(Task t) {
		return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.getStatus());
	}
}
