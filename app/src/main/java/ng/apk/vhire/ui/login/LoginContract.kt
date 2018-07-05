package ng.apk.vhire.ui.login

import com.google.gson.JsonObject
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView
import retrofit2.Call


interface LoginContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {
        fun loginUser(email:String,password:String): Call<JsonObject>
    }

}