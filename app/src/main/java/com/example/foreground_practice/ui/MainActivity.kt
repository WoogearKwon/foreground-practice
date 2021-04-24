package com.example.foreground_practice.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.model.WorkTypeConverters.StateIds.*
import com.example.foreground_practice.R
import com.example.foreground_practice.viewmodel.WorkerViewModel
import com.example.foreground_practice.viewmodel.WorkerViewModel.Companion.WORK_CANCELED
import com.example.foreground_practice.viewmodel.WorkerViewModel.Companion.WORK_FINISHED
import com.example.foreground_practice.viewmodel.WorkerViewModel.Companion.WORK_STARTED
import com.example.foreground_practice.workers.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.awaitAll
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WorkerViewModel
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
        initProperties()
        setLiveDataObservers()
    }

    private fun initProperties() {
        viewModel = ViewModelProviders.of(this).get(WorkerViewModel::class.java)
        pref = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    }


    private fun setButtonState(canStart: Boolean) {
        buttonStart.isEnabled = canStart
        buttonStart.isActivated = canStart
        buttonStop.isEnabled = !canStart
        buttonStop.isActivated = !canStart
    }

    @SuppressLint("SetTextI18n")
    private fun setLiveDataObservers() {
        viewModel.work.observe(this, Observer {
            when(it) {
                WORK_STARTED -> {}
                WORK_FINISHED -> {}
                WORK_CANCELED -> {}
            }
        })

        WorkManager.getInstance(this)
            .getWorkInfosByTagLiveData(WORK_MANAGER_TAG).observe(this, Observer {
                if (it[0].state.isFinished) pref.edit().putInt(PREF_RECORD_NUMBER, 0).apply()
                setButtonState(it[0].state.isFinished)

                tvSavedNumber.text = "Saved Number: ${pref.getInt(PREF_RECORD_NUMBER, 0)}"
                tvSavedTime.text = "Saved Time: ${pref.getString(PREF_RECORD_TIME, "No Time Saved")}"
            })
    }

    private fun setClickListeners() {
        buttonStart.setOnClickListener {
            viewModel.startWork()
        }

        buttonStop.setOnClickListener {
            viewModel.stopWorkManager()
        }
    }
}