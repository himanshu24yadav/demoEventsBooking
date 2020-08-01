package com.example.demoeventsbooking

import android.app.Application
import android.content.Context

class CustomApplicationClass : Application() {

    companion object {
        private var instance:CustomApplicationClass? = null

        fun applicationContext() : Context? {
            return instance?.applicationContext
        }
    }

    init {
        instance = this
    }
}