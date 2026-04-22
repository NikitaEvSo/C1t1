package com.example.c1t1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun SharedAnnotatedText() {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Companion.Bold)) {
                append("Bed: ")
            }
            append("1 king bed")
            append("   ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Companion.Bold)) {
                append("Total number of guests: ")
            }
            append("2")
        })
}