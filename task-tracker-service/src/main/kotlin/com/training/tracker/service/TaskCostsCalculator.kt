package com.training.tracker.service

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class TaskCostsCalculator {

    fun computeAssignCost(): Int = Random.nextInt(10, 21)

    fun computeReward(): Int = Random.nextInt(20, 41)
}