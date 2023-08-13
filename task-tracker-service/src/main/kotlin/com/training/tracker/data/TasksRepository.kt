package com.training.tracker.data

import com.training.tracker.data.model.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TasksRepository : JpaRepository<Task, Long>