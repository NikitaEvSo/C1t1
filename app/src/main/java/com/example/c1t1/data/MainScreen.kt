package com.example.c1t1.data

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.c1t1.MainViewModel
import com.example.c1t1.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(model: MainViewModel) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("The Alps' HOTEL \uD83C\uDDEB\uD83C\uDDF7") }, actions = {
            IconButton(
                onClick = { model.navigateTo(Screens.MyBookingPage) }) {}
        })
    }) {
        val searchState = rememberTextFieldState()
        Surface(
            modifier = Modifier
                .fillMaxSize().background(Color.Gray)
                .padding(it)
        ) {
            Column(Modifier.fillMaxSize().background(Color.Gray), horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(searchState, placeholder = { Text("Search a hotel name") }, modifier = Modifier.fillMaxWidth(0.9f).background(Color.White))
                Spacer(Modifier.height(10.dp))
                LazyColumn {
                    items(
                        if (searchState.text.isEmpty()) {
                        model.hotels
                    } else {
                        model.hotels.filter { it ->
                            it.name.contains(
                                searchState.text.toString(), ignoreCase = true
                            )
                        }
                    }) { hotel: Hotel ->
                        HotelRow(model, hotel)
                    }
                }
            }
        }
    }
}



@Composable
private fun HotelRow(model: MainViewModel,hotel: Hotel) {
    Row(
        Modifier
            .fillMaxWidth().height(125.dp).background(Color.White)
            .padding(10.dp)
    ) {
        Image(painter = painterResource(hotel.image), null)
        Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.Center) {
            Text(hotel.name)
            Row() {
                val avg = getAverageRating(hotel)
                Text(avg.toString())
                repeat((avg / 2).toInt()) {
                    Icon(painterResource(R.drawable.star_on), null)
                }
            }
            Text("${hotel.distance}")
        }
        Spacer(Modifier.width(30.dp))
        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
            Button(onClick = {model.setHotel(hotel)
            model.navigateTo(Screens.BookingPage)}, shape = RectangleShape) {
                Text("Book it")
            }
        }
    }
    Spacer(Modifier.height(18.dp))
}

@Composable
private fun getAverageRating(hotel: Hotel): Double = hotel.ratings.prop.map { it.rate }.average()
