package com.example.foreground_practice.workers

import android.util.Log

fun sleep(time: Long) {
    try {
        Thread.sleep(time, 0)
    } catch (e: InterruptedException) {
        e.message?.let {
            Log.e("SLEEP", it)
        }
    }
}