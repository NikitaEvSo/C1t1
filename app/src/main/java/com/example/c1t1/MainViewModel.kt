package com.example.c1t1

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.c1t1.data.Form
import com.example.c1t1.data.Hotel
import com.example.c1t1.data.RateParam
import com.example.c1t1.data.Rating
import com.example.c1t1.data.Review
import com.example.c1t1.data.Room

enum class Screens{
    MainScreen,BookingPage,Confirm,MyBookingPage
}
class MainViewModel(application: Application) : AndroidViewModel(application) {

    val hotels = listOf(
        Hotel(
            name = "FirstHotel",
            image = R.drawable.ic_launcher_background,
            distance = 2f,
            ratings = Rating(
                prop = listOf(
                    RateParam(
                        title = "Pricing", rate = 7.8F
                    ), RateParam(
                        title = "Service", rate = 8.7F
                    )
                )
            ),
            reviews = listOf(
                Review(
                    name = "User",
                    location = "France ",
                    review = "TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST "
                )
            ),
            rooms = listOf(
                Room(
                    id = 20,
                    description = "TestRoomTestRoomTestRoomTestRoomTestRoom", price = 299
                )
            )
        )
    )
    var bookedRooms : MutableList<Form> = mutableListOf()
    var selectedHotel:Int? = null

    fun getselectedHotel(): Hotel {
        return hotels[selectedHotel!!]
    }
    fun resetSelectedHotel(){
        selectedHotel = null
    }
    fun setHotel(hotel: Hotel){
        selectedHotel = hotels.indexOf(hotel)
    }

    var selectedRoom: Room? = null
    var screen =  mutableStateOf(Screens.MainScreen)
    fun navigateTo(screens: Screens) {
        screen.value= screens
    }
    fun Book(form: Form){
    bookedRooms+=form
    }
}
