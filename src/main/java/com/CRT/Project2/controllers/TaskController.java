package com.CRT.Project2.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CRT.Project2.entities.Task;
import com.CRT.Project2.repositories.TaskRepository;
import com.CRT.Project2.security.CustomUserDetails;

@RestController
@RequestMapping("api/task")
public class TaskController {
	private final TaskRepository taskRepository;
	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Task> createTask(@RequestBody Task task, 
			@AuthenticationPrincipal CustomUserDetails userDetails){
		task.setUserId(userDetails.getUserId());
		if(task.getStatus() == null) {
			task.setStatus("TODO");
		}
		taskRepository.save(task);
		return ResponseEntity.status(201).body(task);
	}
	
	@GetMapping("/get/{id}")
	public Optional<Task> getTask(@PathVariable Long id){
		return taskRepository.findById(id);
	}
}
