package com.training.tracker.controller

import com.training.tracker.controller.model.RegisterRequest
import com.training.tracker.domain.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {

    @Autowired
    private lateinit var userService: UserService

    //RessourceEndPoint:http://localhost:9080/api/admin/register
    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterRequest): ResponseEntity<*> {
        return userService.register(
                email = registerDto.email,
                password = registerDto.password,
                userName = registerDto.name,
                role = registerDto.role,
        )
    }
}