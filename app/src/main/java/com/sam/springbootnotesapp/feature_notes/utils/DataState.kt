package com.sam.springbootnotesapp.feature_notes.utils

import com.sam.springbootnotesapp.feature_notes.domain.model.Notes

sealed class DataState<out T>(val data: T? = null,val message: String? = null){
    class Success<T>(data:T):DataState<T>(data,null)
    class Error(message:String):DataState<Nothing>(message = message)
    object Loading:DataState<Nothing>()
}
