package ng.apk.vhire.ui.base

import android.view.View

interface BaseView<T> {

    var presenter:T
    fun initView()
    fun showLoadingDialog(message:String)
    fun hideLoadingDialog(callback:()->Unit)
    fun showToast(content:String,type:String="info")
    fun onErrorInConnection(callback: () -> Unit)

 }
