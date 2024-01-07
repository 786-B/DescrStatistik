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
                // Check if the number is within the allowed range
                if (it <= 10000.000f) {
                    _numbersList.value = (_numbersList.value + it).sorted()
                } else {
                    // Handle the case where the number is too large, e.g., show an error to the user
                }
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