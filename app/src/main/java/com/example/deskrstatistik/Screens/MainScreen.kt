package com.example.deskrstatistik.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.deskrstatistik.UI_Elements.CharWithHigherLowerSymbol
import com.example.deskrstatistik.UI_Elements.CharWithLowerChar
import com.example.deskrstatistik.UI_Elements.NumberField
import com.example.deskrstatistik.UI_Elements.getCheckIcon
import com.example.deskrstatistik.UI_Elements.getClearIcon
import com.example.deskrstatistik.UI_Elements.getFalseIcon
import com.example.deskrstatistik.UI_Elements.getFormulaText
import com.example.deskrstatistik.UI_Elements.getHeadline
import com.example.deskrstatistik.UI_Elements.getQuantilElements
import com.example.deskrstatistik.Utility.FractionBuilder
import com.example.deskrstatistik.Utility.getArithmeticMean
import com.example.deskrstatistik.Utility.getCoefficientOfVariation
import com.example.deskrstatistik.Utility.getHeadlineDEActiveColor
import com.example.deskrstatistik.Utility.getMedian
import com.example.deskrstatistik.Utility.getModes
import com.example.deskrstatistik.Utility.getQuantile
import com.example.deskrstatistik.Utility.getQuantileDifference
import com.example.deskrstatistik.Utility.getSkewness1
import com.example.deskrstatistik.Utility.getSkewness2
import com.example.deskrstatistik.Utility.getStandardDeviation
import com.example.deskrstatistik.Utility.getSum
import com.example.deskrstatistik.Utility.getVariance
import com.example.deskrstatistik.Utility.getWingSpan
import com.example.deskrstatistik.Utility.isQuantileCalculable
import com.example.deskrstatistik.Utility.isWholeNumber
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
        getHeadline(text = "Summe")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            getFormulaText(text = "∑ ", fontSize = 30)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (numbersList.size > 1) {
                getFormulaText(
                    text = "${numbersList[0]}+..+${numbersList[numbersList.size - 1]} = "
                )
                getFormulaText(text = "$sum", color = Color.Green.copy(alpha = .8f))
            }

        }
        Divider(modifier = Modifier.padding(5.dp))

        //5- x̄
        val arithmeticMean = getArithmeticMean(numbersList)
        getHeadline(text = "Arithmetische Mittel")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            getFormulaText(text = "x̄ := ")
            FractionBuilder(numerator = "1", denominator = "n")
            CharWithHigherLowerSymbol()
            CharWithLowerChar(x = "x", i = "i")
            Spacer(modifier = Modifier.padding(3.dp))
        }
        if (numbersList.size > 1) {
            Divider(modifier = Modifier.width(200.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                getFormulaText(text = "x̄ := ")
                FractionBuilder(
                    numerator = "1",
                    denominator = "${numbersList.size}",
                    lineHeight = 14
                )
                CharWithHigherLowerSymbol(n = "${numbersList.size}")
                getFormulaText(
                    text = "${numbersList[0]}+..+${numbersList[numbersList.size - 1]} = "
                )
                Spacer(modifier = Modifier.padding(3.dp))
                getFormulaText(text = "$arithmeticMean", color = Color.Green.copy(alpha = .8f))
            }
        }
        Divider(modifier = Modifier.padding(5.dp))

        //6- median
        val isOdd = numbersList.size % 2 == 1
        val median = getMedian(numbersList)
        getHeadline(text = "Median")
        Spacer(modifier = Modifier.height(7.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "med", i = "x")
            getFormulaText(text = " := ")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            //isOdd----------
            getFormulaText(text = "n is odd?", fontSize = 11)
            getFormulaText(text = " = x(")
            FractionBuilder(
                numerator = "n+1",
                denominator = " 2",
                lineHeight = 14,
                fontSizeDenominator = 13,
                fontSizeNumerator = 13
            )
            getFormulaText(text = ")")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            //-even-----------
            getFormulaText(text = "n is even? = ", fontSize = 11)
            FractionBuilder(
                numerator = "1",
                denominator = "2",
                fontSizeNumerator = 13,
                fontSizeDenominator = 13
            )
            getFormulaText(text = " (")
            getFormulaText(text = "x(", fontSize = 13)
            FractionBuilder(
                numerator = "n",
                denominator = "2",
                fontSizeNumerator = 11,
                fontSizeDenominator = 11
            )
            getFormulaText(text = ") + x(", fontSize = 13)
            FractionBuilder(
                numerator = "n+1",
                denominator = " 2",
                fontSizeNumerator = 11,
                fontSizeDenominator = 11
            )
            getFormulaText(text = ")", fontSize = 13)
            getFormulaText(text = ")")
            //----------------
        }
        Spacer(modifier = Modifier.height(5.dp))

        if (numbersList.size > 1) {
            Divider(modifier = Modifier.width(200.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {

                if (isOdd) {
                    getFormulaText(text = "(odd!) = ", fontSize = 11)
                    //isOdd----------
                    getFormulaText(text = "x(")
                    FractionBuilder(
                        numerator = "${numbersList.size}+1",
                        denominator = " 2"
                    )
                    getFormulaText(text = ") = ")
                    getFormulaText(text = "$median", color = Color.Green.copy(alpha = .8f))
                } else {
                    getFormulaText(text = "(even!) = ", fontSize = 11)
                    //-even-----------
                    FractionBuilder(
                        numerator = "1",
                        denominator = "2",
                        fontSizeNumerator = 13,
                        fontSizeDenominator = 13
                    )
                    getFormulaText(text = " (")
                    getFormulaText(text = "x(", fontSize = 13)
                    FractionBuilder(
                        numerator = "${numbersList.size}",
                        denominator = "2",
                        fontSizeNumerator = 11,
                        fontSizeDenominator = 11
                    )
                    getFormulaText(text = ") + x(", fontSize = 13)
                    FractionBuilder(
                        numerator = "${numbersList.size}+1",
                        denominator = " 2",
                        fontSizeNumerator = 11,
                        fontSizeDenominator = 11
                    )
                    getFormulaText(text = ")", fontSize = 13)
                    getFormulaText(text = ") = ")
                    getFormulaText(text = "$median", color = Color.Green.copy(alpha = .8f))
                    //----------------
                }
            }
        }

        Divider(modifier = Modifier.padding(5.dp))

        //7- modus
        val mod = getModes(numbersList)
        getHeadline(text = "Modus")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "mod", i = "x")
            getFormulaText(text = " :=")
            getFormulaText(text = " $mod", color = Color.Green.copy(alpha = .8f))

        }
        Divider(modifier = Modifier.padding(5.dp))

        //8- quantil

        Row(verticalAlignment = Alignment.CenterVertically) {
            getFormulaText(text = "p-Quantil, p ∈ [0,1], ")
            getFormulaText(text = "x⌊")
            getFormulaText(text = "np", fontSize = 11)
            getFormulaText(text = "⌋ ≤ ")
            CharWithLowerChar(x = "Q", i = "p")
            getFormulaText(text = " ≥")
            getFormulaText(text = "x⌊")
            getFormulaText(text = "np", fontSize = 11)
            getFormulaText(text = "⌋")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            getFormulaText(text = "Vorausgesetzt: p > ")
            FractionBuilder(numerator = "1", denominator = "n")
            getFormulaText(text = " & p < 1")
        }
        Divider(modifier = Modifier.width(200.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "Q", i = "p")
            getFormulaText(text = " := ")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            //isOdd----------
            getFormulaText(text = "n is odd?", fontSize = 11)
            getFormulaText(text = " = x(⌈np⌉)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            //-even-----------
            getFormulaText(text = "n is even? = ", fontSize = 11)
            FractionBuilder(
                numerator = "x(np)+x(np+1)",
                denominator = "     2",
                fontSizeNumerator = 13,
                fontSizeDenominator = 13,
                lineHeight = 14
            )
        }
        Divider(modifier = Modifier.width(300.dp), thickness = 2.dp)
        Spacer(modifier = Modifier.height(9.dp))
        // 0.1
        getQuantilElements(numbersList = numbersList, quantile = 0.1, quantileText = "0.1")

        Divider(modifier = Modifier.width(200.dp))

        // 0.25
        getQuantilElements(numbersList = numbersList, quantile = 0.25, quantileText = "0.25")
        Divider(modifier = Modifier.width(200.dp))

        //0.75
        getQuantilElements(numbersList = numbersList, quantile = 0.75, quantileText = "0.75")
        Divider(modifier = Modifier.width(200.dp))

        //0.9
        getQuantilElements(numbersList = numbersList, quantile = 0.9, quantileText = "0.9")
        Divider(modifier = Modifier.padding(5.dp))

        // Quartilsdifference
        getHeadline(text = "Quartilsdifferenz")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val colorFor25 = if (isQuantileCalculable(
                    numbersList,
                    0.25
                )
            ) Color.Green.copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.8f)
            val colorFor75 = if (isQuantileCalculable(
                    numbersList,
                    0.75
                )
            ) Color.Green.copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.8f)
            CharWithLowerChar(x = "qd", i = "x")
            getFormulaText(text = " := ")
            getFormulaText(text = " q⁴ - ", color = colorFor75)
            CharWithLowerChar(x = "q", i = "4", color = colorFor25)
        }
        if (isQuantileCalculable(numbersList, 0.75) && isQuantileCalculable(numbersList, 0.25)) {
            Divider(modifier = Modifier.width(200.dp))
            Spacer(modifier = Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {

                getFormulaText(
                    text = "= ${
                        getQuantile(
                            numbersList,
                            0.75
                        )
                    } - ${getQuantile(numbersList, 0.25)} = ${getQuantileDifference(numbersList)}"
                )
            }
        }



        Divider(modifier = Modifier.padding(5.dp))
        // 9-Varianz
        getHeadline(text = "Varianz")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "var", i = "x")
            getFormulaText(text = " = ")
            CharWithHigherLowerSymbol(
                symbol = "s",
                n = "2",
                i = "x",
                fontsize = 20,
                bottomFromBaselineN = 0,
                height = 21
            )
            getFormulaText(text = " := ")
            CharWithHigherLowerSymbol()
            CharWithLowerChar(x = " (x", i = "i")
            getFormulaText(text = " - x̄)²")
            getFormulaText(text = "/", fontSize = 19, color = Color.White)
            getFormulaText(text = "(n-1)=")
        }
        getFormulaText(text = "${getVariance(numbersList)}", color = Color.Green.copy(alpha = .8f))
        Divider(modifier = Modifier.padding(5.dp))
        // 10-Standardabweichung
        getHeadline(text = "Standardabweichung")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "s", i = "x")
            getFormulaText(text = " := √(")
            CharWithLowerChar(x = "var", i = "x", fontsizeX = 13, fontsizeY = 8)
            getFormulaText(text = ") = ${getStandardDeviation(numbersList)}")
        }
        Divider(modifier = Modifier.padding(5.dp))
        // 11-Spannweite
        getHeadline(text = "Spannweite")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "R", i = "x")
            getFormulaText(text = " := max(x) - min(x) = ")
            CharWithLowerChar(x = "(x", i = "(n)")
            CharWithLowerChar(x = "-x", i = "(1)")
            getFormulaText(text = ") = ")
        }
        getFormulaText(text = "${getWingSpan(numbersList)}", color = Color.Green.copy(alpha = .8f))
        Divider(modifier = Modifier.padding(5.dp))
        // 12-Variationskoeffizient (relative Standardabweichung)
        getHeadline(text = "Variationskoeffizient")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "v", i = "x")
            getFormulaText(text = " := ")
            CharWithLowerChar(x = "s", i = "x")
            getFormulaText(text = "/", color = Color.White)
            getFormulaText(text = "x̄ = ")
            getFormulaText(text = "${getCoefficientOfVariation(numbersList)}", color = Color.Green.copy(alpha = .8f))
        }
        Divider(modifier = Modifier.padding(5.dp))
        // 13-Schiefekoeffizient-1
        getHeadline(text = "Schiefekoeffizienten")
        Spacer(modifier = Modifier.height(7.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "sk", i = "1")
            getFormulaText(text = " := ")
            getFormulaText(text = "(", fontSize = 23, color = Color.White)
            FractionBuilder(numerator = " 1", denominator = "n-1")
            CharWithHigherLowerSymbol()
            CharWithLowerChar(x = "(x", i = "i")
            getFormulaText(text = "-x̄)³")
            getFormulaText(text = ")", fontSize = 23, color = Color.White)
            getFormulaText(text = "/", fontSize = 23, color = Color.White)
            CharWithHigherLowerSymbol(
                symbol = "s",
                n = "3",
                i = "x",
                fontsize = 20,
                bottomFromBaselineN = 0,
                height = 21
            )
        }
        getFormulaText(text = getSkewness1(numbersList), color = Color.Green.copy(alpha = .8f))
        Divider(modifier = Modifier.padding(2.dp))


        // 13-Schiefekoeffizient-2

        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "sk", i = "2")
            getFormulaText(text = " := ")
            getFormulaText(text = "(", fontSize = 23, color = Color.White)
            getFormulaText(text = "x̄-")
            CharWithLowerChar(x = "mod", i = "x", fontsizeX = 15, fontsizeY = 7)
            getFormulaText(text = ")", fontSize = 23, color = Color.White)
            getFormulaText(text = "/", fontSize = 23, color = Color.White)
            CharWithLowerChar(x = "s", i = "x")
        }
        getFormulaText(text = getSkewness2(numbersList), color = Color.Green.copy(alpha = .8f))
        Divider(modifier = Modifier.padding(2.dp))


        // 13-Schiefekoeffizient-3

        Row(verticalAlignment = Alignment.CenterVertically) {
            CharWithLowerChar(x = "sk", i = "3")
            getFormulaText(text = " := ")
            getFormulaText(text = "(", fontSize = 23, color = Color.White)
            getFormulaText(text = "x̄-")
            CharWithLowerChar(x = "med", i = "x", fontsizeX = 15, fontsizeY = 7)
            getFormulaText(text = ")", fontSize = 23, color = Color.White)
            getFormulaText(text = "/", fontSize = 23, color = Color.White)
            CharWithLowerChar(x = "s", i = "x")
        }
        getFormulaText(text = getSkewness1(numbersList), color = Color.Green.copy(alpha = .8f))
        Divider(modifier = Modifier.padding(5.dp))
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

