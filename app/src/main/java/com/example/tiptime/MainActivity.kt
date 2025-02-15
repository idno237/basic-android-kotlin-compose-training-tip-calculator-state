/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    //TipTimeLayout()
                    GallerieApp()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }

    val amount = amountInput.toDoubleOrNull() ?: 0.0

    var roundUp by remember { mutableStateOf(false) }

    var tipInput by remember { mutableStateOf("") }

    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, tipPercent, roundUp)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
       // keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun RoundTheTipRow(roundUp: Boolean, onRoundUpChanged: (Boolean) -> Unit,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(text = stringResource(R.string.round_up_tip))

        Switch(checked = roundUp, onCheckedChange = onRoundUpChanged,)
    }

}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency.
 * Example would be "$10.00".
 */
private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount

    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}
//....................................................................................
@Composable
fun GallerieApp(){

    val images = listOf(
        R.drawable.kotlin_1,
        R.drawable.kotlin_2,
        R.drawable.kotlin_3,
        R.drawable.kotlin_4,
        R.drawable.kotlin_5 )

    val descriptions = listOf(
        listOf("Titre : Afrique", "Artiste : IDRISS", "Année : 2024"),
        listOf("Titre : Danse", "Artiste : NOUPEYI", "Année : 2023"),
        listOf("Titre : Femme", "Artiste : Corein", "Année : 2022"),
        listOf("Titre : Ocean", "Artiste : enfants", "Année : 2024"),
        listOf("Titre : Oiseau", "Artiste : Rondelle", "Année : 2023")
    )



    //  trace de l'image actuellement affichée
    var currentImageIndex by remember { mutableStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F5F5))
    {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            Spacer(modifier = Modifier.height(100.dp))
            // Bloc pour l'image
            Image(imageResId = images[currentImageIndex])
            Spacer(modifier = Modifier.height(16.dp))

            // Bloc pour la description
            Description(titre = descriptions[currentImageIndex][0], artiste = descriptions[currentImageIndex][1], annee = descriptions[currentImageIndex][2])
            Spacer(modifier = Modifier.height(16.dp))

            // Bloc pour les boutons
            Buttons(
                onPreviousClick = {
                    if (currentImageIndex > 0) {
                        currentImageIndex--
                    }
                },
                onNextClick = {
                    if (currentImageIndex < images.size - 1) {
                        currentImageIndex++
                    }
                }
            )
        }
    }

}

                       //****************//
@Composable
fun Image(imageResId: Int){
    Surface(modifier = Modifier.fillMaxWidth(0.9f).height(300.dp).padding(8.dp),color = Color.LightGray,shadowElevation = 20.dp)
    {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Image de l'œuvre",
            modifier = Modifier.fillMaxSize()
        )

    }

}


                        //****************//

@Composable
fun Description(titre: String, artiste: String, annee: String){
    Surface(
        modifier = Modifier.fillMaxWidth().padding(16.dp),color = Color.LightGray, shape = RoundedCornerShape(19.dp), shadowElevation = 10.dp
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth())
        {
            Text(text = titre, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = artiste, fontSize = 24.sp)
            Text(text = annee, fontSize = 20.sp)
        }


    }

}
                       //****************//

@Composable
fun Buttons(onPreviousClick: () -> Unit, onNextClick: () -> Unit){

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
    {
        Button(onClick = { onPreviousClick() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)))

        {
            Icon(
                imageVector = Icons.Default.ArrowBack, // Icône de flèche vers la gauche
                contentDescription = "Précédent",
                tint = Color.White // Couleur de l'icône
            )

            Spacer(modifier = Modifier.width(8.dp)) // Espace entre l'icône et le texte

            Text("Précédent", color = Color.White)
        }


        Button(onClick = { onNextClick() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)))
        {
            Icon(
                contentDescription = "Suivant" ,
                imageVector = Icons.Default.ArrowForward, // Icône de flèche vers la droite
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espace entre l'icône et le texte

            Text("Suivant", color = Color.White) //

        }
    }


}




//....................................................................................
@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        //TipTimeLayout()
        GallerieApp()
    }
}

