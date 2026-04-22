package com.example.c1t1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.c1t1.data.Room

@Composable
fun BookingPage(model: MainViewModel) {
    val horPState = rememberPagerState() { 2 }
    var currentPage by remember { mutableIntStateOf(0) }

    @Composable
    fun ChangePage(pageID: Int) {
        LaunchedEffect(currentPage) { horPState.scrollToPage(pageID) }
    }
    Scaffold(
        topBar = {
            SharedTopBar(model, Screens.MainScreen, "Booking")
        }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { currentPage = 0 },
                    Modifier.weight(1F),
                    content = { Text("Guest reviews") })
                TextButton(
                    onClick = { currentPage = 1 },
                    Modifier.weight(1f),
                    content = { Text("Room selection") })
            }
            Column(Modifier.fillMaxSize().padding(10.dp)) {
            ChangePage(currentPage)
            val selectedHotel = model.getselectedHotel()
            Text(selectedHotel.name)
            HorizontalPager(horPState) {

                    when (it) {
                        0 -> {
                            GuestReviewsPage(model)
                        }

                        1 -> {
                            Column(Modifier.padding(10.dp)) {
                                Text("Rooms")
                                LazyColumn() {
                                    items(selectedHotel.rooms) {
                                        RoomRow(it, model)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RoomRow(room: Room, model: MainViewModel) {
    Card(shape = RectangleShape, onClick = {
        model.selectedRoom = room
        model.navigateTo(Screens.Confirm)
    }) {
        Row() {
            Column() {
                Text(room.title)
                SharedAnnotatedText()
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    Text(room.description)
                }
            }
            Text("Э ${room.price}")

        }
    }
}

