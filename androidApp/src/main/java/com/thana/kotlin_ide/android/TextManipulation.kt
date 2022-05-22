package com.thana.kotlin_ide.android

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.thana.kotlin_ide.android.theme.*

val varKeyWords = listOf("val", "var", "class", "fun","data")
val dataTypes = listOf("String", "Int", "Long", "Char", "Boolean", "Array", "List")
val otherKeywords = listOf("main")
val funKeywords = listOf("print")
val suggestionList =
    concatenate(varKeyWords, dataTypes, otherKeywords, funKeywords) as MutableList<String>

class ColorsTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.toString()),
            OffsetMapping.Identity
        )
    }
}

fun buildAnnotatedStringWithColors(code: String): AnnotatedString {
    val pattern = "[ \\t]"
    val words: List<String> = code.split(pattern.toRegex())
    val builder = AnnotatedString.Builder()
    for (word in words) {
        when (word) {
            in varKeyWords -> {
                builder.withStyle(
                    style = SpanStyle(
                        color = VarKeyWords,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$word ")
                }
            }
            in dataTypes -> {
                builder.withStyle(
                    style = SpanStyle(
                        color = DataTypes,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$word ")
                }
            }
            in otherKeywords -> {
                builder.withStyle(
                    style = SpanStyle(
                        color = OtherKeyWords,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$word ")
                }
            }
            in funKeywords -> {
                builder.withStyle(
                    style = SpanStyle(
                        color = funKeyWords,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$word ")
                }
            }
            else -> {
                builder.withStyle(
                    style = SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$word ")
                }
            }
        }
    }
    return builder.toAnnotatedString()
}

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}

@Composable
fun Numbering(text: String) {
    val listOfNumbers = mutableListOf<Int>()
    var count = 0
    Row {
        text.forEach { char ->
            if (char == '\n') {
                count++
                listOfNumbers.add(count)
            }
        }
        LazyColumn {
            items(listOfNumbers.size) { index ->
                Text(text = "${listOfNumbers[index]}")
            }
        }
    }
}
