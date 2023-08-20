package com.training.accounting.user.data

import com.training.accounting.user.data.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findUserByEmail(email: String): User
    fun findUserByPublicId(publicId: String): User

}