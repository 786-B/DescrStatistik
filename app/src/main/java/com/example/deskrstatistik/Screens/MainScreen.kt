package com.example.deskrstatistik.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deskrstatistik.UI_Elements.NumberField
import com.example.deskrstatistik.UI_Elements.getButton
import com.example.deskrstatistik.Utility.FormulaBeginn
import com.example.deskrstatistik.Utility.SummationSymbol
import com.example.deskrstatistik.Utility.arithmeticMean
import com.example.deskrstatistik.Utility.getSum
import com.example.deskrstatistik.Utility.xWithLowerChar
import com.example.deskrstatistik.ViewModel.MainViewModel

@Composable
fun MainScreen(
    navCtrl: NavController,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val scrollState = rememberScrollState()
    val numbers by viewModel.numbers.observeAsState("")

    //---for futher operations, get the numbers
    val numbersList = viewModel.getNumbersList()

    //empty list check
    val listIsNotEmpty = !numbersList.isNullOrEmpty()

    //  val divider = Divider(modifier = Modifier.padding(5.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.dp)
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //1- contaminated text
        Text(text = numbers, color = Color.LightGray)
        Divider(modifier = Modifier.padding(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            //2- textfield
            NumberField(initialValue = "", onNumbersChange = viewModel::onNumbersChange)

            //3- clearButton
            getButton(listIsNotEmpty) {
                viewModel.emptyNumbersList()
            }
        }
        Divider(modifier = Modifier.padding(5.dp))
        //4- sum
        val sum = if (listIsNotEmpty) getSum(numbersList) else 0.000
        Text(text = "∑ $sum", color = Color.LightGray)
        Divider(modifier = Modifier.padding(5.dp))

        //5- mean
        val arithmeticMean = if (listIsNotEmpty) arithmeticMean(numbersList) else 0.000
        Row(verticalAlignment = Alignment.CenterVertically) {
            FormulaBeginn(x = "x̄ := ", numerator = "1", denominator = "n")
            SummationSymbol()
            xWithLowerChar(x = "x", i = "i")
            Spacer(modifier = Modifier.padding(3.dp))
            Text(text = "= $arithmeticMean", color = Color.LightGray)
        }
        Divider(modifier = Modifier.padding(5.dp))


    }
}
