package com.example.c1t1

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.c1t1.data.Form
import com.example.c1t1.data.Pay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfirmPage(model: MainViewModel) {
    val hotel = remember { model.getselectedHotel() }
    val room = remember { model.selectedRoom }
    val formatter = remember {
        DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", Locale.ENGLISH)
    }
    Scaffold(topBar = { SharedTopBar(model = model, Screens.BookingPage, "Booking Confirm") }) {

        Column(Modifier.padding(it)) {
            Text("You are going to reserve:")
            Text(hotel.name)
            Box(
                Modifier
                    .fillMaxWidth(0.95f)
                    .padding(8.dp)
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Text(room!!.title)
                    Row() {
                        SharedAnnotatedText()
                        Text("Э ${room.price}")
                    }
                    Column(Modifier.fillMaxSize()) {
                        Text("Form")
                        val statefirst = rememberTextFieldState("First Name")
                        val statelast = rememberTextFieldState("Last Name")
                        var Date1 by remember { mutableStateOf(LocalDate.now()) }
                        var Date2 by remember { mutableStateOf(LocalDate.now()) }
                        val stateAdults = rememberTextFieldState("2")
                        val stateChildren = rememberTextFieldState("Last Name")
                        val stateRoom = rememberTextFieldState("Last Name")
                        var isbusiness by remember { mutableStateOf(false) }
                        var PayType by remember { mutableStateOf(Pay.Cash) }
                        Row() {
                            CustomTextField("First Name", statefirst)
                            CustomTextField("Last Name", statelast)
                        }
                        Row() {
                            SimpleDatePicker("Check-in date", Date1) { Date1 = it }
                            SimpleDatePicker("Check-out date", Date2) { Date2 = it }
                        }
                        Text(room.title)

                        Row() {
                            CustomTextField("Adults", stateAdults)
                            CustomTextField("Children", stateChildren)
                            CustomTextField("Room", stateRoom)
                        }
                        Column() {
                            Text("Travel for business?")
                            Row() {
                                Row() {
                                    RadioButton(
                                        selected = !isbusiness, onClick = { isbusiness = false })
                                    Text("For sightseeing")
                                }
                                Row() {
                                    RadioButton(
                                        selected = isbusiness, onClick = { isbusiness = true })
                                    Column() {
                                        Text("+ Э 150")
                                        Text("For business with a meeting room")
                                    }
                                }
                            }
                        }
                        Row {
                            Column() {
                                Text("Which way to pay?")
                                Row() {
                                    Row() {
                                        RadioButton(
                                            selected = PayType == Pay.Cash,
                                            onClick = { PayType = Pay.Cash })
                                        Text("Cash")
                                    }
                                    Row() {
                                        RadioButton(
                                            selected = PayType == Pay.CreditCard,
                                            onClick = { PayType = Pay.CreditCard })
                                        Text("Credit card")
                                    }
                                    Row() {
                                        RadioButton(
                                            selected = PayType == Pay.Epay,
                                            onClick = { PayType = Pay.Epay })
                                        Column() {
                                            Text("E-Pay")
                                        }
                                    }
                                }
                            }
                            val stayPrice = calculateDatePrice(room.price, Date1, Date2)
                            val businessFee = if (isbusiness) 150 else 0
                            Text("Э ${stayPrice + businessFee}")
                        }
                        Button(modifier = Modifier.fillMaxWidth(),onClick = {
                            model.Book(Form(
                                firstName = statefirst.text.toString(),
                                lastName = statelast.text.toString(),
                                checkInDate = Date1!!,
                                checkOutDate = Date2!!,
                                hotel = hotel,
                                room = room,
                                isBusiness = isbusiness,
                                payType = PayType
                            )
                            )
                            model.navigateTo(Screens.MyBookingPage)
                        }) { Text("Book now")}
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDatePrice(price: Int, startDate: LocalDate, endDate: LocalDate): Int {
    val days = ChronoUnit.DAYS.between(startDate, endDate)
    return (price * days.coerceAtLeast(0)).toInt()
}

@Composable
fun RowScope.CustomTextField(label: String, state: TextFieldState) {
    Column(
        Modifier
            .weight(1f)
            .padding(10.dp)
    ) {
        Text(label)
        TextField(state)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RowScope.SimpleDatePicker(label: String, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    Column(
        Modifier
            .weight(1f)
            .padding(10.dp)
    ) {
        Text(label)
        OutlinedTextField(
            value = selectedDate.format(formatter),
            onValueChange = {},
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    DatePickerDialog(context, { _, y, m, d ->
                        onDateSelected(LocalDate.of(y, m + 1, d))
                    }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth).show()
                }
        )
    }
}