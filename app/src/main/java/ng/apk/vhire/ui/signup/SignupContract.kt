package ng.apk.vhire.ui.signup

import com.google.gson.JsonObject
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView
import org.json.JSONObject

/**
 * Created by othree on 3/21/18.
 */
interface SignupContract {

    interface View : BaseView<Presenter> {
        fun onSuccessfulsignup(data:JsonObject?)
    }

    interface Presenter : BasePresenter {
        fun signup (data:JsonObject)
    }
}