package com.example.demoeventsbooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.demoeventsbooking.databinding.ActivityMainBinding
import com.example.demoeventsbooking.helpers.UtilMethods
import com.example.demoeventsbooking.helpers.isNetworkAvailable
import com.example.demoeventsbooking.homeSection.dataManager.ViewModelEventsList

class MainActivity : AppCompatActivity() {

    private var mEventViewModel : ViewModelEventsList? = null
    private lateinit var mLayoutBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        init()
    }

    private fun init() {
        initObject()
        initFetchEventData()
        initObserver()
    }

    private fun initObject() {
        mEventViewModel = ViewModelProvider(this).get(ViewModelEventsList::class.java)
    }

    private fun initObserver() {
        mEventViewModel?.getAllEvents()?.observe(this, Observer {

        })
    }

    private fun initFetchEventData() {
        if(isNetworkAvailable()) {
            mEventViewModel?.fetchAllEvents()
        } else {
            UtilMethods.showToast(this,"Network not available")
        }
    }
}