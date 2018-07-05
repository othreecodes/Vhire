package ng.apk.vhire.ui.login

import com.google.gson.JsonObject
import ng.apk.vhire.data.remote.api.Http
import retrofit2.Call


class LoginPresenter(val mView: LoginContract.View,val  http: Http) : LoginContract.Presenter {

    override fun loginUser(email: String, password: String): Call<JsonObject> {

        val data = JsonObject()
        data.addProperty("email", email)
        data.addProperty("password", password)

        return http.login(data)
    }

    override fun start() {
        mView.initView()

    }

    override fun finalize() {

    }

    init {
        mView.presenter = this

    }

}