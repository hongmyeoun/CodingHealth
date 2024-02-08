package com.example.codinghealth.model

import kotlin.random.Random

object LottoModel {
    fun genRandomNumbers(): List<Int> {
        val randomNumbers = mutableListOf<Int>()

        while (randomNumbers.size < 7) {
            val number = Random.nextInt(1, 46)
            if (!randomNumbers.contains(number)) {
                randomNumbers.add(number)
            }
        }

        return randomNumbers.sorted()
    }
}