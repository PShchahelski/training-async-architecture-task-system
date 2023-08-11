package com.training.auth.controller

class UserNotFoundException(override val message: String) : Exception(message)