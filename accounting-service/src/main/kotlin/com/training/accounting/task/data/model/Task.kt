package com.training.accounting.task.data.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tasks")
class Task(
    val title: String,
    val jiraId: String? = null,
    val userPublicId: String,
    val publicId: String,
    val assignCost: Int,
    val reward: Int,
    @Id
    @GeneratedValue
    val id: Long = -1
)