package com.example.c1t1

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.c1t1.data.Form
import com.example.c1t1.data.Pay
import com.example.c1t1.data.formated
import com.example.c1t1.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfirmPage(model: MainViewModel) {
    val hotel = remember { model.getselectedHotel() }
    val room = remember { model.selectedRoom!! }
    val formatter = remember {
        DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", Locale.ENGLISH)
    }
    Scaffold(topBar = { SharedTopBar(model = model, Screens.BookingPage, "Booking Confirm") }) {

        Column(Modifier.padding(it).background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) { Text("You are going to reserve:") }
            Spacer(Modifier.height(100.dp))
            Text(
                hotel.name,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.95f)
                            .background(Color.White).padding(8.dp)
                    ) {
                        Text(room.title, fontWeight = FontWeight.Bold)
                        Row() {
                            SharedAnnotatedText()
                            Text("Э ${room.price}",fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Column(Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {

                        Text("Form", fontWeight = FontWeight.Bold)

                        val statefirst = rememberTextFieldState("First Name")
                        val statelast = rememberTextFieldState("Last Name")
                        var Date1 by remember { mutableStateOf(LocalDate.now()) }
                        var Date2 by remember { mutableStateOf(LocalDate.now()) }
                        val stateAdults = rememberTextFieldState("2")
                        val stateChildren = rememberTextFieldState("0")
                        val stateRoom = rememberTextFieldState("1")
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
                        Text(room.title, fontWeight = FontWeight.Bold)

                        Row() {
                            CustomTextField("Adults", stateAdults)
                            CustomTextField("Children", stateChildren)
                            CustomTextField("Room", stateRoom)
                        }
                        Column() {
                            Text("Travel for business?")
                            Row() {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = !isbusiness, onClick = { isbusiness = false })
                                    Text("For sightseeing",fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = isbusiness, onClick = { isbusiness = true })
                                    Column() {
                                        Text("+ Э 150", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("For business with a meeting room",fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        Row (verticalAlignment = Alignment.Bottom){
                            Column() {
                                Text("Which way to pay?")
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = PayType == Pay.Cash,
                                            onClick = { PayType = Pay.Cash })
                                        Text("Cash",fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = PayType == Pay.CreditCard,
                                            onClick = { PayType = Pay.CreditCard })
                                        Text("Credit card",fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = PayType == Pay.Epay,
                                            onClick = { PayType = Pay.Epay })
                                            Text("E-Pay",fontSize = 12.sp, fontWeight = FontWeight.Bold)

                                    }
                                    val stayPrice = calculateDatePrice(room.price, Date1, Date2)
                                    val businessFee = if (isbusiness) 150 else 0
                                    Spacer(Modifier.width(40.dp))
                                    Text("Э ${stayPrice + businessFee}", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.headlineLarge.fontSize)
                                }
                            }
                        }
                        Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), shape = RectangleShape, onClick = {
                            model.Book(
                                Form(
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
                        }) { Text("Book now") }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RowScope.CustomTextField(label: String, state: TextFieldState) {
    Column(
        Modifier
            .weight(1f)
            .padding(8.dp)
    ) {
        Text(label)
        TextField(modifier = Modifier.height(50.dp), state = state, textStyle = androidx.compose.ui.text.TextStyle(
            fontWeight = FontWeight.Bold), colors = TextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RowScope.SimpleDatePicker(
    label: String, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current

    Column(
        Modifier
            .weight(1f)
            .padding(10.dp)
    ) {
        Text(label)
        OutlinedTextField(
            value = selectedDate.formated(),
            onValueChange = {},
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth().height(50.dp)
                .clickable {
                    DatePickerDialog(
                        context, { _, y, m, d ->
                            onDateSelected(LocalDate.of(y, m + 1, d))
                        }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth
                    ).show()
                }, textStyle = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold)
            , colors = TextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))
    }
}