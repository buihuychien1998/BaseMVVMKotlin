package com.example.basemvvm.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm.R
import com.example.basemvvm.common.utils.DialogUtils

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(), View.OnClickListener {
    protected lateinit var viewBinding: DB
    protected lateinit var viewModel: VM
    protected lateinit var sharedViewModel: SharedViewModel
    abstract fun initViewModel()
    abstract val layoutId: Int

    /**
     * Initialize views
     *
     * @param savedInstanceState
     */
    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun initObservers()

    /**
     * Initialize listeners
     */
    abstract fun initListeners()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView<DB>(this, layoutId)
        initViewModel()
        initSharedViewModel()
        initViews(savedInstanceState)
        initBaseObservers()
        initObservers()
        initListeners()
    }

    private fun initBaseObservers() {
        viewModel.getIsLoading().observe(this, Observer { isLoading ->
            if (isLoading) {
                showProgressDialog(R.string.app_name)
                return@Observer
            }
            dismissProgressDialog()
        })
    }

    private fun initSharedViewModel() {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
    }

    /**
     * This function to get ViewModel
     *
     * @param clazz
     * @return
     */
    protected fun getViewModel(clazz: Class<VM>): VM {
        return ViewModelProvider(this)[clazz]
    }

    /**
     * This function is used to show soft keyboard
     * この機能は、ソフトキーボードを表示するために使用されます
     */
    fun showKeyboard() {
        val view = currentFocus ?: return
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(
            view,
            InputMethodManager.SHOW_IMPLICIT
        )
    }

    fun forceShowKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * This function is used to hide soft keyboard
     * この機能は、ソフトキーボードを非表示にするために使用されます
     */
    fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
            ?: //            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            return
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showProgressDialog(message: String?) {
        DialogUtils.showProgressDialog(this, message)
    }

    fun showProgressDialog(@StringRes messageId: Int) {
        showProgressDialog(getString(messageId))
    }

    fun dismissProgressDialog() {
        DialogUtils.dismissProgressDialog()
    }
}