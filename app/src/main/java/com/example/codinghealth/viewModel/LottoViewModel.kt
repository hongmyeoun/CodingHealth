package com.example.codinghealth.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codinghealth.model.LottoModel.genRandomNumbers
import com.example.codinghealth.utill.Utill
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LottoViewModel: ViewModel() {
    private val _randomNumbers = MutableStateFlow(listOf<Int>())
    val randomNumbers: StateFlow<List<Int>> = _randomNumbers
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex
    private val _showBonusNumber = MutableStateFlow(false)
    val showBonusNumber: StateFlow<Boolean> = _showBonusNumber

    fun showBonusNumber(){
        _showBonusNumber.value = true
    }

    fun noShowBonusNumber(){
        _showBonusNumber.value = false
    }

    fun resetNumber(){
        _randomNumbers.value = listOf()
        _currentIndex.value = 0
    }

    fun getRandomColor(): Color {
        return Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1f
        )
    }

    fun getTextColor(color: Color): Color {
        return if (Utill.calculateBrightness(color) > 0.5f) Color.Black else Color.White
    }

    fun genNumbers(){
        viewModelScope.launch {
            _randomNumbers.value = genRandomNumbers()
            repeat(6){
                _currentIndex.value = it
                delay(1000)
            }
        }
    }

    fun genNumberEnabled(): Boolean {
        return _randomNumbers.value.isEmpty()
    }

    fun resetNumberEnabled(): Boolean{
        return _randomNumbers.value.isNotEmpty()
    }

    fun checkBonusEnabled(): Boolean {
        return _randomNumbers.value.isNotEmpty() && !_showBonusNumber.value
    }
}
