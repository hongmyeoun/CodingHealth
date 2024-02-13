package com.example.codinghealth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NaviViewModel: ViewModel() {
    private val _text = MutableStateFlow(NaviRoute.HOME.name)
    val text: StateFlow<String> = _text

    fun iconClicked(text: String){
        when(text){
            NaviRoute.HOME.name -> _text.value = NaviRoute.HOME.name
            NaviRoute.CART.name -> _text.value = NaviRoute.CART.name
            NaviRoute.BELL.name -> _text.value = NaviRoute.BELL.name
            NaviRoute.SEARCH.name -> _text.value = NaviRoute.SEARCH.name
            NaviRoute.SETTING.name -> _text.value = NaviRoute.SETTING.name
        }
    }
}