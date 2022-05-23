package com.thana.kotlin_ide.android

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.thana.kotlin_ide.android.theme.LightBackground
import com.thana.kotlin_ide.android.theme.PrimaryColor
import com.thana.kotlin_ide.android.theme.SecondaryColor
import com.thana.kotlin_ide.android.theme.cursorColor


@Composable
fun Editor(output: String, isLoading: Boolean) {
    val startText = "fun main (args: Array <String> ) { "
    val endText = "    \n\n\n\n\n}"
    val text = remember { mutableStateOf(TextFieldValue("$startText $endText")) }

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            LinearProgressIndicator(
                color = SecondaryColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Column {
        Toolbar(script = text.value.text,
            onClear = { text.value = TextFieldValue("$startText $endText") },
            onShare = { context -> shareCode(context, text.value.text) }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(start = 16.dp, end = 16.dp, top = 0.dp),
            value = text.value,
            onValueChange = { newText -> text.value = newText },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LightBackground,
                cursorColor = cursorColor
            ),
            shape = RoundedCornerShape(0.dp),
            visualTransformation = ColorsTransformation()
        )

        Text(
            text = "Output:",
            color = PrimaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 6.dp, top = 6.dp),
        )

        val filteredOutput = output.replace("jdoodle.kt:", "")

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),

            value = filteredOutput,
            onValueChange = { },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LightBackground
            )
        )
    }
}

fun shareCode(context: Context, script: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, script)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}