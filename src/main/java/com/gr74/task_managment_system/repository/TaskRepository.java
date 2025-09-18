package com.gr74.task_managment_system.repository;

import com.gr74.task_managment_system.model.Task;
import com.gr74.task_managment_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser(User user);
}
