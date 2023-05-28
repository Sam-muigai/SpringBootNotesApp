package com.sam.springbootnotesapp.util

sealed class UiEvents{
    data class Navigate(val route:String):UiEvents()
    data class ShowSnackBar(val message:String):UiEvents()
    object PopBackStack:UiEvents()
}
