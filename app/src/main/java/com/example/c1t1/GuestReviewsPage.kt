package com.example.c1t1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.c1t1.data.Hotel

@Composable
fun GuestReviewsPage(model: MainViewModel,selectedHotel: Hotel = model.getselectedHotel()) {
    Column() {

        Card(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            RatingsPanel(selectedHotel)
            Spacer(Modifier.Companion.height(20.dp))
            ReviewPanel(selectedHotel)
        }
    }
}

@Composable
private fun ReviewPanel(selectedHotel: Hotel) {
    Column() {
        Text("Reviews")
        LazyRow() {
            items(selectedHotel.reviews) {
                Card(
                    shape = RectangleShape, modifier = Modifier.Companion
                        .defaultMinSize(
                            minWidth = 150.dp, minHeight = 300.dp
                        )
                        .width(150.dp)
                        .padding(8.dp)
                ) {
                    Column() {
                        Row() {
                            LetterBox(it.name)
                            Column() {
                                Text(it.name)
                                Text(it.location)
                            }
                        }
                        Text(it.review)
                    }
                }
                Spacer(Modifier.Companion.width(16.dp))
            }
        }
    }
}

@Composable
fun LetterBox(name: String) {
    Box(modifier = Modifier.Companion.background(Color.Companion.Gray)) {
        Text(
            name.get(0).toString(),
            textAlign = TextAlign.Companion.Center,
            modifier = Modifier.Companion.align(
                Alignment.Companion.Center
            )
        )
    }
}

@Composable
private fun RatingsPanel(selectedHotel: Hotel) {
    Column {
        Text("Ratings")
        LazyColumn() {
            items(selectedHotel.ratings.prop) {
                Row(
                    Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it.title)
                    Text(it.rate.toString())
                }
                Box(
                    Modifier.Companion
                        .fillMaxWidth()
                        .height(4.dp)
                ) {
                    Box(
                        Modifier.Companion
                            .align(Alignment.Companion.CenterStart)
                            .fillMaxWidth(it.rate / 10)
                            .height(4.dp)
                            .background(Color.Companion.Gray)
                    )
                }
            }
        }
    }
}