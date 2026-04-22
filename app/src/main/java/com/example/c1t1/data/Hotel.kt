package com.example.c1t1.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Hotel(
    val name: String,
    val image: Int,
    val distance: Float,
    val ratings: Rating,
    val reviews: List<Review>,
    val rooms: List<Room>
)

data class Form(
    var firstName: String = "",
    var lastName: String = "",
    var checkInDate: LocalDate,
    var checkOutDate: LocalDate,
    val hotel: Hotel,
    val room: Room,
    var isBusiness: Boolean,
    var payType: Pay
) {
    val totalPrice: Int
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val days = ChronoUnit.DAYS.between(checkInDate, checkOutDate)
                .coerceAtLeast(0)
            val basePrice = (room.price * days).toInt()
            val fee = if (isBusiness) 150 else 0
            return basePrice + fee
        }
}
