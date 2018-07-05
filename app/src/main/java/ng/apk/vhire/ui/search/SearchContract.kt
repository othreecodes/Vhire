package ng.apk.vhire.ui.search

import android.content.Context
import android.content.Intent
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView
import ng.apk.vhire.models.JobCategory
import retrofit2.Call

public interface SearchContract {

    interface View : BaseView<Presenter> {
        fun showLocationDialog()
        fun UpdateToolbarText(text: String)

        fun showRefreshing()
        fun hideRefreshing()
    }


    interface Presenter : BasePresenter {
        fun saveLocationPreferences(data: Intent?, context: Context?)
        fun fetchCategories(): Call<List<JobCategory>>
    }
}
