package com.example.basemvvm.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.example.basemvvm.R
import com.example.basemvvm.common.Status
import com.example.basemvvm.databinding.ActivityMainBinding
import com.example.basemvvm.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun initViewModel() {
        viewModel = getViewModel(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?) {

    }

    override fun initObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        result.data?.let { users ->
                            Log.d(
                                "TAG",
                                "initObservers: SUCCESS ${users}"
                            )
                        }
                        dismissProgressDialog()
                    }
                    Status.ERROR -> {
                        dismissProgressDialog()
                        Log.d("TAG", "initObservers: ERROR")
                    }
                    Status.LOADING -> {
                        showProgressDialog(R.string.app_name)
                        Log.d("TAG", "initObservers: LOADING")
                    }
                }
            }
        })
    }

    override fun initListeners() {
        viewBinding.tv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv -> {
                viewModel.fetchUsers()
            }
        }
    }

}