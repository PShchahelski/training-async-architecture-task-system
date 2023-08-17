package com.training.tracker.controller

import com.training.tracker.controller.model.LoginRequest
import com.training.tracker.domain.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    //RessourceEndPoint:http://localhost:9080/api/user/authenticate
    @PostMapping("/authenticate")
    fun login(@RequestBody loginDto: LoginRequest): String {
        return userService.authenticate(
                email = loginDto.email,
                password = loginDto.password,
        )
    }
}