package com.example.c1t1

import android.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBar(model: MainViewModel, backScreen: Screens, title: String) {
    TopAppBar(title = { Text(title) }, navigationIcon = {
        IconButton(onClick = {
            model.navigateTo(
                backScreen
            )
        }) {
            Icon(painterResource(R.drawable.ic_menu_close_clear_cancel), null)
        }
    })
}