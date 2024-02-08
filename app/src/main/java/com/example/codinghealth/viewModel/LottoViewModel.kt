package com.example.codinghealth.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codinghealth.model.LottoModel.genRandomNumbers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LottoViewModel: ViewModel() {
    private val _randomNumbers = MutableStateFlow(listOf<Int>())
    val randomNumbers: StateFlow<List<Int>> = _randomNumbers
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex
    private val _showBonusNumber = MutableStateFlow(false)
    val showBonusNumber: StateFlow<Boolean> = _showBonusNumber
    private val _randomColorMap = MutableStateFlow(mutableMapOf<Int, Color>())

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
