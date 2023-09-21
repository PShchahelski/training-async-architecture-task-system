package com.training.accounting.task.data.model

import com.training.accounting.transaction.data.model.Transaction
import com.training.accounting.user.data.model.User
import jakarta.persistence.*

@Entity
@Table(name = "tasks")
class Task(
	val title: String,
	@Column(name = "jira_id")
	val jiraId: String? = null,
	@Column(name = "public_id", updatable = false, nullable = false, unique = true)
	val publicId: String,
	@Column(name = "assign_cost")
	val assignCost: Int,
	val reward: Int,
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	val user: User,
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	val transactions: List<Transaction> = emptyList(),
	@Id
	@GeneratedValue
	val id: Long = -1,
)