package com.example.deskrstatistik.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.deskrstatistik.UI_Elements.getHeadline

@Composable
fun getCharacteristics(){
    
    getHeadline(text = "Merkmal")
    Text(text = "Ein Merkmal ist eine abstrahierende Eigenschaften von 1-n Beobachtungen (Merkmalsausprägungen=Merkmalswerten), die pro Merkmalsträger erfasst werden. Sie besteht aus Merkmalsausprägungen, die an Merkmalsträgern einer Gesamtheit erfasst werden.")
    Text(text = "Ein Beobachtung: univariates Merkmal, erfasst nur eine Beobachtung pro Merkmalsträger.")
    Text(text = "Zwei Beobachtungen: bivariates Merkmal, erfasst nur zwei Beobachtung pro Merkmalsträger.")
    Text(text = "n-Beobachtungen: multivariates Merkmal, erfasst n-Beobachtung pro Merkmalsträger.")

    Text(text = "Merkmale können quantitativ oder qualitativ sein. Quantitative Merkmale werden auch metrisch oder kardinal genannt. Ihre Ausprägungen sind ganzzahlig oder reellwertig (Addier-, subtrahier-, multiplizierbar, Bsp.: Gewicht, Größe, Alter)")

    Text(text = "Ein qualitative Merkmal ist die Eigenschaft eines Merkmalsträgers. Ein qualitatives Merkmal kann ordinal oder nominal sein.")

    Text(text = "Ordinales Merkmal: kann angeordnet, aber nicht addiert oder multipliziert werden (Bsp.: Schulnoten, Gesundheitszustand..)\n" +
            "Nominale Merkmale: erlauben auch kein Sortieren ihrer Ausprägungen (Bsp.: Religion, Geschlecht, Beruf..)")

    Text(text = "Nominale und ordinale Merkmale sind diskret. Bei einem diskreten Merkmal ist die Ausprägung mit natürlichen Zahlen abzählbar (Geschlecht, Einwohnerzahl..).")
    Text(text = "Metrisch, kardinale Merkmale können diskret oder stetig ein. Stetige Merkmale können überabzählbar viele Werte annehmen, z.B. jede reelle Zahl in einem Intervall (Temperatur, Lebensdauer, Farbstärke..)")

}