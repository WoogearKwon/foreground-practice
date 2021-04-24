package com.example.foreground_practice.workers

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

const val PREF_KEY = "work_manager_pref"
const val PREF_RECORD_NUMBER = "number"
const val PREF_RECORD_TIME = "time"

const val WORK_MANAGER_TAG = "work_manager_tag"

fun sleep(time: Long) {
    try {
        Thread.sleep(time, 0)
    } catch (e: InterruptedException) {
        e.message?.let {
            Log.e("SLEEP", it)
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    return sdf.format(Date())
}