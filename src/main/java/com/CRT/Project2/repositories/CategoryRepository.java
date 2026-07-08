package com.CRT.Project2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CRT.Project2.entities.Category;
import com.CRT.Project2.entities.Task;
import com.CRT.Project2.entities.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	public User findByUserId(Long UserId);
	
	public List<Task> findTaskByUserId(Long UserId);
	
	public List<Category> findCategoryByUserId(Long userId);
	
	public Category findByName(String name);
	
}
