package com.app.task_api.repository;

import com.app.task_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(int userid);
}
