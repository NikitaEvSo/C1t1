package com.example.c1t1

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.c1t1.data.Pay
import com.example.c1t1.data.formated

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyBookingPage(model: MainViewModel) {
    Scaffold(topBar = { SharedTopBar(model = model, Screens.MainScreen, "My bookings") }) {
        Column(
            Modifier
                .fillMaxSize().background(Color.LightGray)
                .padding(it)
        ) {
            Text("List of my bookings", fontWeight = FontWeight.Bold)
            LazyColumn(Modifier.padding(16.dp)) {
                itemsIndexed(model.bookedRooms) { index, item ->
                    Row(Modifier.fillMaxWidth().padding(12.dp).background(Color.White).padding(10.dp)) {
                        Text(index.toString(), fontWeight = FontWeight.Bold, modifier = Modifier.align(
                            Alignment.CenterVertically))
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.fillMaxWidth()) {
                            Text("${item.firstName} ${item.lastName}" , fontWeight = FontWeight.Bold)
                            Text(item.hotel.name , fontWeight = FontWeight.Bold)
                            Text("${item.checkInDate.formated()} to ${item.checkOutDate.formated()}")
                            Text("${item.adults} Adults ${item.children} Children ${item.rooms} Room")
                            Text(
                                "${
                                    if (item.isBusiness) "For sightseeing" else "For Business"
                                }  Pay with ${
                                    when (item.payType) {
                                        Pay.Cash -> "cash"
                                        Pay.CreditCard -> "credit card"
                                        Pay.Epay -> "e-pay"
                                    }
                                }"
                            )
                            Text("Э ${ item.totalPrice }", modifier = Modifier.align(Alignment.End), fontWeight = FontWeight.Bold)
                        }
                    }
                }

            }
        }
    }
}