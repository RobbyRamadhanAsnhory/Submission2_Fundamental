package com.example.subsmission2_robbyramadhana_md_07

sealed class Desc<T>(val data: T?  = null, val message: String? = null) {
    class Success<T>(data: T?): Desc<T>(data)
    class Error<T>(message: String?, data: T? = null): Desc<T>(data, message)
    class Loading<T>(data: T? = null): Desc<T>(data)
}
