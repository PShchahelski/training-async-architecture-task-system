package com.training.tracker.controller

class UserNotFoundException(override val message: String) : Exception(message)