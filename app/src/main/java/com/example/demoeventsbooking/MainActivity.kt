package com.example.demoeventsbooking

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoeventsbooking.databinding.ActivityMainBinding
import com.example.demoeventsbooking.helpers.UtilConstants
import com.example.demoeventsbooking.helpers.isNetworkAvailable
import com.example.demoeventsbooking.homeSection.adapter.EventAdapter
import com.example.demoeventsbooking.homeSection.dataManager.MasterListEventModel
import com.example.demoeventsbooking.homeSection.dataManager.ViewModelEventsList

const val VIEW_FOR_SUCCESS = 100
const val VIEW_FOR_FAILURE = 200
const val VIEW_FOR_NO_DATA = 300
const val VIEW_FOR_NETWORK_NOT_AVAILABLE = 400

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private var mEventViewModel : ViewModelEventsList? = null
    private lateinit var mLayoutBinding: ActivityMainBinding
    private var mLinearLayoutManager: RecyclerView.LayoutManager? = null
    private var mEventAdapter:EventAdapter? = null
    private var mEventList:ArrayList<MasterListEventModel> = ArrayList()
    private var mSelectedCity: String = UtilConstants.DEFAULT_CITY
    private var mPopupMenu: PopupMenu? = null

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
            initFetchEventData()
        }

        mLayoutBinding.locationIv.setOnClickListener {
            mPopupMenu?.show()
        }

        mPopupMenu?.setOnMenuItemClickListener(this@MainActivity)
    }

    private fun initView() {
        mLayoutBinding.rvEvents.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayoutManager
            itemAnimator = null
        }
        mPopupMenu?.inflate(R.menu.menu_locations)
        mPopupMenu?.gravity = Gravity.END
        mPopupMenu?.menu?.findItem(R.id.online_item)?.isChecked = true
    }

    private fun initObject() {
        mEventViewModel = ViewModelProvider(this).get(ViewModelEventsList::class.java)
        mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPopupMenu =  PopupMenu(this@MainActivity, mLayoutBinding.locationIv)
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
            mLayoutBinding.errorGroup.visibility = View.GONE
            mLayoutBinding.rvEvents.visibility = View.GONE
            mLayoutBinding.progressGroup.visibility = View.VISIBLE
            mEventViewModel?.fetchAllEvents(mSelectedCity)
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.online_item ->{
                mSelectedCity = UtilConstants.DEFAULT_CITY
            }
            R.id.delhi_item ->{
                mSelectedCity = UtilConstants.DELHI_CITY
            }
            R.id.lucknow_item ->{
                mSelectedCity = UtilConstants.LUCKNOW_CITY
            }
            R.id.pune_item ->{
                mSelectedCity = UtilConstants.PUNE_CITY
            }
            R.id.kolkata_item ->{
                mSelectedCity = UtilConstants.KOLKATA_CITY
            }
            R.id.mumbai_item ->{
                mSelectedCity = UtilConstants.MUMBAI_CITY
            }
            else -> {
                return false
            }
        }
        item.isChecked = true
        initFetchEventData()
        return true
    }
}