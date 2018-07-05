package ng.apk.vhire.ui.search

import android.content.Context
import android.content.Intent
import android.util.Log

import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete

import es.dmoral.prefs.Prefs
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.JobCategory
import retrofit2.Call

/**
 * Created by othree on 2/17/18.
 */

public class SearchPresenter constructor( val mView: SearchContract.View,val http: Http) : SearchContract.Presenter {
    override fun saveLocationPreferences(data: Intent?, context: Context?) {

        Prefs.with(context!!).writeBoolean("has_chosen_aim_in_life", true)
        val place = PlaceAutocomplete.getPlace(context, data)
        Log.i("TAG", "Place: " + place.name)
        Log.wtf("TAG", place.address.toString())
        Log.wtf("TAG", place.latLng.toString())
        Prefs.with(context).writeBoolean("has_chosen_aim_in_life", true)
        Prefs.with(context).write("place", place.name.toString())
        Prefs.with(context).write("address", place.address.toString())
        Prefs.with(context).write("long_lat", place.latLng.toString())
        this.mView.UpdateToolbarText(place.address.toString())

    }


    init {
        mView.presenter = this


    }

    override fun start() {
            mView.initView()

    }

    override fun finalize() {

    }


    override fun fetchCategories(): Call<List<JobCategory>> {
        return this.http.categories

    }
}
