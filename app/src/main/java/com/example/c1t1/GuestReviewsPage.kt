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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.c1t1.data.Hotel

@Composable
fun GuestReviewsPage(model: MainViewModel, selectedHotel: Hotel = model.getselectedHotel()) {
    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RatingsPanel(selectedHotel)
            Spacer(Modifier.height(20.dp))
            ReviewPanel(selectedHotel)
        }
    }
}

@Composable
private fun ReviewPanel(selectedHotel: Hotel) {
    Column(Modifier.fillMaxWidth().background(Color.White).padding(8.dp)) {
        Text("Reviews",
            fontWeight = FontWeight.Bold,)
        LazyRow() {
            items(selectedHotel.reviews) {
                Column (
                    modifier = Modifier.Companion
                        .defaultMinSize(
                            minWidth = 150.dp, minHeight = 300.dp
                        )
                        .width(150.dp).background(Color.White)
                        .padding(8.dp)
                ) {
                    Column() {
                        Row() {
                            LetterBox(it.name)
                            Column() {
                                Text(
                                    it.name,
                                    fontWeight = FontWeight.Bold,
                                )
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
    Box(
        modifier = Modifier
            .size(30.dp)
            .padding(3.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    ) {
        Text(
            name[0].toString(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(
                Alignment.Center
            ),
            color = Color.White
        )
    }
}

@Composable
private fun RatingsPanel(selectedHotel: Hotel) {
    Column(Modifier.background(Color.White).padding(8.dp)) {
        Text("Ratings",
            fontWeight = FontWeight.Bold,)
        LazyColumn() {
            items(selectedHotel.ratings.prop) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it.title)
                    Text(it.rate.toString())
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(4.dp).background(Color.LightGray)
                ) {
                    Box(
                        Modifier.Companion
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(it.rate / 10)
                            .height(4.dp)
                            .background(Color.Gray)
                    )
                }
            }
        }
    }
}