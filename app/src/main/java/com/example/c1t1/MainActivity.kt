package com.example.c1t1

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.c1t1.data.MainScreen
import com.example.c1t1.ui.theme.C1t1Theme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val model = remember {
                MainViewModel(
                    application = application
                )
            }
            C1t1Theme {
                Navigation(model)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(model: MainViewModel) {
    when (model.screen.value) {
        Screens.MainScreen -> MainScreen(
            model = model
        )
        Screens.BookingPage-> BookingPage(model)
        Screens.Confirm -> ConfirmPage(model)
        Screens.MyBookingPage -> MyBookingPage(model)
    }
}

