package com.example.deskrstatistik.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.example.deskrstatistik.Navigation.NavRoutes
import com.example.deskrstatistik.UI_Elements.NumberField
import com.example.deskrstatistik.UI_Elements.getClearIcon
import com.example.deskrstatistik.UI_Elements.getInfoIcon
import com.example.deskrstatistik.Utility.CharWithLowerChar
import com.example.deskrstatistik.Utility.SummationSymbol
import com.example.deskrstatistik.Utility.arithmeticMean
import com.example.deskrstatistik.Utility.fractionBuilder
import com.example.deskrstatistik.Utility.getFormulaText
import com.example.deskrstatistik.Utility.getSum
import com.example.deskrstatistik.Utility.median
import com.example.deskrstatistik.Utility.modes
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
            getClearIcon(listIsNotEmpty) {
                viewModel.emptyNumbersList()
            }
        }
        Divider(modifier = Modifier.padding(5.dp))

        //4- sum
        val sum = if (listIsNotEmpty) getSum(numbersList) else 0.0
        Row(verticalAlignment = Alignment.CenterVertically) {
            getFormulaText(text = "∑ ", fontSize = 30)
            getFormulaText(text = "$sum")
        }
        Divider(modifier = Modifier.padding(5.dp))

        //5- x̄
        val arithmeticMean = arithmeticMean(numbersList)
        Row(verticalAlignment = Alignment.CenterVertically) {
            getFormulaText(text = "x̄ := ")
            fractionBuilder(x = "1", y = "n")
            SummationSymbol()
            CharWithLowerChar(x = "x", i = "i")
            Spacer(modifier = Modifier.padding(3.dp))
            getFormulaText(text = "= $arithmeticMean")
        }
        Divider(modifier = Modifier.padding(5.dp))

        //6- median
        val isOdd = numbersList.size % 2 == 1
        val median = numbersList.median()
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "med", i = "x")
            getFormulaText(text = " := ")

            if (listIsNotEmpty) {
                if (isOdd) {
                    //isOdd----------
                    getFormulaText(text = "x(")
                    fractionBuilder(x = "n+1", y = " 2", lineheight = 14)
                    getFormulaText(text = ") = ")
                } else {
                    //-even-----------
                    fractionBuilder(x = "1", y = "2", fontSizeX = 13, fontSizeY = 13)
                    getFormulaText(text = " (")
                    getFormulaText(text = "x(", fontSize = 13)
                    fractionBuilder(x = "n", y = "2", fontSizeX = 11, fontSizeY = 11)
                    getFormulaText(text = ")", fontSize = 13)
                    getFormulaText(text = " + x(", fontSize = 13)
                    fractionBuilder(x = "n+1", y = " 2", fontSizeX = 11, fontSizeY = 11)
                    getFormulaText(text = ")", fontSize = 13)
                    getFormulaText(text = ") = ")
                    //----------------
                }
            }
            Text(text = "$median", color = Color.LightGray, fontSize = 20.sp)
        }

        Divider(modifier = Modifier.padding(5.dp))

        //7- modus
        val mod = numbersList.modes()
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "mod", i = "x")
            getFormulaText(text = " := ")
            getFormulaText(text = "$mod")
        }

        Divider(modifier = Modifier.padding(5.dp))

        //8- weighted x̄
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "x̄", i = "w")
            getFormulaText(text = " := ")
            SummationSymbol()
            CharWithLowerChar(x = "w", i = "i")
            CharWithLowerChar(x = "x", i = "i")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "w", i = "i")
            getFormulaText(text = " = ")
            CharWithLowerChar(x = "n", i = "i")
            getFormulaText(text = "/n")
            getInfoIcon {navCtrl.navigate(NavRoutes.WeightedMeanInfo.name)}
        }

        Divider(modifier = Modifier.padding(5.dp))
    }
}