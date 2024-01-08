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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deskrstatistik.UI_Elements.NumberField
import com.example.deskrstatistik.UI_Elements.getClearIcon
import com.example.deskrstatistik.UI_Elements.getFormulaText
import com.example.deskrstatistik.Utility.CharWithLowerChar
import com.example.deskrstatistik.Utility.CharWithHigherLowerSymbol
import com.example.deskrstatistik.Utility.arithmeticMean
import com.example.deskrstatistik.Utility.calculateQuantile
import com.example.deskrstatistik.Utility.calculateVariance
import com.example.deskrstatistik.Utility.fractionBuilder
import com.example.deskrstatistik.Utility.getCoefficientOfVariation
import com.example.deskrstatistik.Utility.getQuantile
import com.example.deskrstatistik.Utility.getQuantileDifference
import com.example.deskrstatistik.Utility.getStandardDeviation
import com.example.deskrstatistik.Utility.getSum
import com.example.deskrstatistik.Utility.getVariance
import com.example.deskrstatistik.Utility.getWingSpan
import com.example.deskrstatistik.Utility.isQuantileCalculable
import com.example.deskrstatistik.Utility.isWholeNumber
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
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
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
            CharWithHigherLowerSymbol()
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
                    getFormulaText(text = ") + x(", fontSize = 13)
                    fractionBuilder(x = "n+1", y = " 2", fontSizeX = 11, fontSizeY = 11)
                    getFormulaText(text = ")", fontSize = 13)
                    getFormulaText(text = ") = ")
                    //----------------
                }
            }
            getFormulaText(text = "$median")
        }

        Divider(modifier = Modifier.padding(5.dp))

        //7- modus
        val mod = numbersList.modes()
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "mod", i = "x")
            getFormulaText(text = " := $mod")
        }
        Divider(modifier = Modifier.padding(5.dp))

        //8- quantil
        // 0.1
        if (isQuantileCalculable(numbersList, 0.1)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val q01 = getQuantile(numbersList, 0.1)
                CharWithLowerChar(x = "q", i = "0.1")
                getFormulaText(text = " = ")
                if (isWholeNumber(0.1 * numbersList.size)) {
                    fractionBuilder(
                        x = "1",
                        y = "2",
                        lineheight = 14,
                        fontSizeX = 13,
                        fontSizeY = 13
                    )
                    getFormulaText(" (x(np) + x(np+1)) = ")
                    getFormulaText("$q01")
                } else {
                    getFormulaText(text = "⌈np⌉ = $q01")
                }
            }
            Divider(modifier = Modifier.padding(5.dp))
        }
        // 0.25
        if (isQuantileCalculable(numbersList, 0.25)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CharWithLowerChar(x = "q", i = "4")
                getFormulaText(text = " = ")
                CharWithLowerChar(x = "q", i = "0.25")
                getFormulaText(text = " = ")
                val q025 = getQuantile(numbersList, 0.25)
                if (isWholeNumber((0.25 * numbersList.size))) {
                    fractionBuilder(
                        x = "1",
                        y = "2",
                        lineheight = 14,
                        fontSizeX = 13,
                        fontSizeY = 13
                    )
                    getFormulaText(" (x(np) + x(np+1)) = ")
                    getFormulaText("$q025")
                } else {
                    getFormulaText(text = "⌈np⌉ = $q025")
                }
            }
            Divider(modifier = Modifier.padding(5.dp))
        }
        //0.75
        if (isQuantileCalculable(numbersList, 0.75)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                getFormulaText(text = "q⁴ = ")
                CharWithLowerChar(x = "q", i = "0.75")
                getFormulaText(text = " = ")
                val q075 = getQuantile(numbersList, 0.75)
                if (isWholeNumber(0.75 * numbersList.size)) {
                    fractionBuilder(
                        x = "1",
                        y = "2",
                        lineheight = 14,
                        fontSizeX = 13,
                        fontSizeY = 13
                    )
                    getFormulaText(" (x(np) + x(np+1)) = ")
                    getFormulaText("$q075")
                } else {
                    getFormulaText(text = "⌈np⌉ = $q075")
                }
            }
            Divider(modifier = Modifier.padding(5.dp))
        }
        //0.9
        if (isQuantileCalculable(numbersList, 0.9)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CharWithLowerChar(x = "q", i = "0.9")
                getFormulaText(text = " = ")
                val q09 = getQuantile(numbersList, 0.9)
                if (isWholeNumber(0.9)) {
                    fractionBuilder(
                        x = "1",
                        y = "2",
                        lineheight = 14,
                        fontSizeX = 13,
                        fontSizeY = 13
                    )
                    getFormulaText(" (x(np) + x(np+1)) = ")
                    getFormulaText("$q09")
                } else {
                    getFormulaText(text = "⌈np⌉ = $q09")
                }
            }
            Divider(modifier = Modifier.padding(5.dp))
        }

        // Quartilsdifference
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "qd", i = "x")
            getFormulaText(text = " := q⁴ - ")
            CharWithLowerChar(x = "q", i = "4")
            CharWithLowerChar(x = "var", i = "x")
            getFormulaText(text = ") = ${getQuantileDifference(numbersList)}")
        }


        // 9-Varianz
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "var", i = "x")
            getFormulaText(text = " = ")
            CharWithHigherLowerSymbol(symbol = "s", n = "²", i = "x")
            getFormulaText(text = " := ")
            CharWithHigherLowerSymbol()
            fractionBuilder(x = "(xi - x̄)²", y = "(n-1)")
            getFormulaText(text = " = ${getVariance(numbersList)}")
        }

        // 10-Standardabweichung
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "s", i = "x")
            getFormulaText(text = " := √(")
            CharWithLowerChar(x = "var", i = "x")
            getFormulaText(text = ") = ${getStandardDeviation(numbersList)}")
        }

        // 11-Spannweite
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "R", i = "x")
            getFormulaText(text = " := max(x) - min(x) = ")
            CharWithLowerChar(x = "x", i = "(n)")
            CharWithLowerChar(x = "x", i = "(1)")
            getFormulaText(text = ") = ${getWingSpan(numbersList)}")
        }

        // 12-Variationskoeffizient (relative Standardabweichung)
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "v", i = "x")
            getFormulaText(text = " := ")
            fractionBuilder(x = "sx", y = "̄x̄")
            getFormulaText(text = ") = ${getCoefficientOfVariation(numbersList)}")
        }

    }

    /*
    //X- weighted x̄
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

    Divider(modifier = Modifier.padding(5.dp))*/
}
