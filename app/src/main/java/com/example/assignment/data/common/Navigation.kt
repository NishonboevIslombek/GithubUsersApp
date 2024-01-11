package com.example.assignment.data.common

sealed class Navigation(val route: String) {
    object HomeScreen : Navigation("home_screen")
    object InfoScreen : Navigation("info_screen")
}