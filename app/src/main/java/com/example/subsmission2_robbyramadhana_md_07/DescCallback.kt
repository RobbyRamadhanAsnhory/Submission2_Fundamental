package com.example.subsmission2_robbyramadhana_md_07

import android.view.View

interface DescCallback<T> {

    fun onSuccess(data: T)
    fun onLoading()
    fun onFailed(message: String?)

    val invisible: Int
        get() = View.INVISIBLE

    val visible: Int
        get() = View.VISIBLE
}