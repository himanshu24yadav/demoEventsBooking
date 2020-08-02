package com.example.demoeventsbooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoeventsbooking.databinding.ActivityMainBinding
import com.example.demoeventsbooking.helpers.UtilConstants
import com.example.demoeventsbooking.helpers.UtilMethods
import com.example.demoeventsbooking.helpers.isNetworkAvailable
import com.example.demoeventsbooking.homeSection.adapter.EventAdapter
import com.example.demoeventsbooking.homeSection.dataManager.MasterListEventModel
import com.example.demoeventsbooking.homeSection.dataManager.ViewModelEventsList

class MainActivity : AppCompatActivity() {

    private var mEventViewModel : ViewModelEventsList? = null
    private lateinit var mLayoutBinding: ActivityMainBinding
    private var mLinearLayoutManager: RecyclerView.LayoutManager? = null
    private var mEventAdapter:EventAdapter? = null
    private var mEventList:ArrayList<MasterListEventModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        init()
    }

    private fun init() {
        initObject()
        initFetchEventData()
        initObserver()
        initView()
    }

    private fun initView() {
        mLayoutBinding.rvEvents.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayoutManager
            itemAnimator = null
        }
    }

    private fun initObject() {
        mEventViewModel = ViewModelProvider(this).get(ViewModelEventsList::class.java)
        mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initObserver() {
        mEventViewModel?.getAllEvents()?.observe(this, Observer {

            when(it.status) {
                UtilConstants.STATUS_SUCCESS -> {
                    mEventList.clear()
                    mEventList.addAll(it.eventData)
                    setEventAdapter()
                }
                UtilConstants.STATUS_FAILURE -> {

                }
                UtilConstants.STATUS_NO_DATA -> {

                }
                else -> {

                }
            }

        })
    }

    private fun initFetchEventData() {
        if(isNetworkAvailable()) {
            mEventViewModel?.fetchAllEvents()
        } else {
            UtilMethods.showToast(this,"Network not available")
        }
    }

    private fun setEventAdapter() {
        if (mEventAdapter != null) {
            mEventAdapter!!.updateList(mEventList)
            mEventAdapter!!.notifyDataSetChanged()
        } else {
            mEventAdapter = EventAdapter(this, mEventList)
            mLayoutBinding.rvEvents.adapter = mEventAdapter
        }
    }
}