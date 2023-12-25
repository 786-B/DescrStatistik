package com.example.deskrstatistik.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.deskrstatistik.Utility.roundToThreeDecimalPlaces
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

        private val _numbersList = MutableStateFlow<List<Float>>(listOf())
        private val _numbers = _numbersList.map { list ->
            list.sorted().joinToString(separator = " | ") { number ->
                roundToThreeDecimalPlaces(number)
            }
        }.asLiveData()

        val numbers: LiveData<String> = _numbers

        fun onNumbersChange(input: String) {
            viewModelScope.launch {
                // Your existing logic to validate the input
                val number = input.toFloatOrNull()
                number?.let {
                    _numbersList.value = (_numbersList.value + it).sorted()
                }
            }
        }
    fun getNumbersList(): List<Float> {
        return _numbersList.value
    }

    fun emptyNumbersList(){
        _numbersList.value = listOf()
    }
}