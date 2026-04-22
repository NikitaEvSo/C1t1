package com.example.c1t1.data

data class Rating(
    val prop: List<RateParam>
)

data class RateParam
    (
    val title: String, val rate: Float
)
