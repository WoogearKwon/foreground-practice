package com.example.foreground_practice.workers

import android.util.Log

const val PREF_KEY = "work_manager_pref"
const val PREF_RECORD_NUMBER = "number"

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