package com.example.demoeventsbooking

import android.os.Bundle
import android.view.View
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

const val VIEW_FOR_SUCCESS = 100
const val VIEW_FOR_FAILURE = 200
const val VIEW_FOR_NO_DATA = 300
const val VIEW_FOR_NETWORK_NOT_AVAILABLE = 400

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
        initListeners()
    }

    private fun initListeners() {
        mLayoutBinding.retryTv.setOnClickListener {
            if(isNetworkAvailable()) {
                mLayoutBinding.errorGroup.visibility = View.GONE
                mLayoutBinding.rvEvents.visibility = View.GONE
                mLayoutBinding.progressGroup.visibility = View.VISIBLE
                initFetchEventData()
            } else {
                UtilMethods.showToast(this,"No network")
            }
        }
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
                    updateView(VIEW_FOR_SUCCESS)
                }
                UtilConstants.STATUS_FAILURE -> {
                    updateView(VIEW_FOR_FAILURE)
                }
                UtilConstants.STATUS_NO_DATA -> {
                    updateView(VIEW_FOR_NO_DATA)
                }
                else -> {
                    updateView(VIEW_FOR_FAILURE)
                }
            }
        })
    }

    private fun initFetchEventData() {
        if(isNetworkAvailable()) {
            mEventViewModel?.fetchAllEvents()
        } else {
            updateView(VIEW_FOR_NETWORK_NOT_AVAILABLE)
        }
    }

    private fun updateView(viewType:Int) {
        mLayoutBinding.progressGroup.visibility = View.GONE
        when(viewType) {
            VIEW_FOR_SUCCESS -> {
                mLayoutBinding.errorGroup.visibility = View.GONE
                mLayoutBinding.rvEvents.visibility = View.VISIBLE
                setEventAdapter()
            }
            VIEW_FOR_NO_DATA -> {
                mLayoutBinding.errorGroup.visibility = View.VISIBLE
                mLayoutBinding.rvEvents.visibility = View.GONE
                mLayoutBinding.errorTv.text = getString(R.string.no_events_available_text)
            }
            VIEW_FOR_FAILURE -> {
                mLayoutBinding.errorGroup.visibility = View.VISIBLE
                mLayoutBinding.rvEvents.visibility = View.GONE
                mLayoutBinding.errorTv.text = getString(R.string.something_went_wrong)
            }
            VIEW_FOR_NETWORK_NOT_AVAILABLE -> {
                mLayoutBinding.errorGroup.visibility = View.VISIBLE
                mLayoutBinding.rvEvents.visibility = View.GONE
                mLayoutBinding.errorTv.text = getString(R.string.no_network_text)
            }
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