package com.training.tracker.data.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
	val email: String,
	val name: String,
	val role: String,
	@Column(name = "public_id", updatable = false, nullable = false)
	val publicId: String,
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	val tasks: List<Task> = emptyList(),
	@Id
	@GeneratedValue
	val id: Long = -1,
)