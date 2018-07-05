package ng.apk.vhire.ui.search


import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.util.Preconditions.checkNotNull
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.gson.Gson
import es.dmoral.prefs.Prefs
import kotterknife.bindView
import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.JobCategory
import ng.apk.vhire.ui.base.BaseFragment
import ng.apk.vhire.ui.categories.CategoriesActivity
import ng.apk.vhire.utils.RecyclerTouchListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment(), SearchContract.View {
    override lateinit var presenter: SearchContract.Presenter

    private var PLACE_AUTOCOMPLETE_REQUEST_CODE = 101


    val toolbar: Toolbar by bindView(R.id.toolbar)


    val categoriesListRecyclerView: RecyclerView by bindView(R.id.categoriesList)


    val refreshLayout: SwipeRefreshLayout by bindView(R.id.refresher)
    private lateinit var mCategoryGridAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        SearchPresenter(this, Http(context))


        presenter.start()
    }


    private var categories: List<JobCategory>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search, container, false)



        return v
    }

    private fun updateRecyclerView() {
        showRefreshing()
        categoriesListRecyclerView.layoutManager = GridLayoutManager(activity, NUM_COL)
        presenter.fetchCategories().enqueue(object : Callback<List<JobCategory>> {
            override fun onResponse(call: Call<List<JobCategory>>, response: Response<List<JobCategory>>) {


                if (response.isSuccessful) {
                    categories = response.body()
                    mCategoryGridAdapter = SearchAdapter(activity, response.body())
                    categoriesListRecyclerView.adapter = mCategoryGridAdapter
//                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                }

                hideRefreshing()
//                showToast("got heree")
//                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<JobCategory>>, t: Throwable) {
//                showToast("called something else")
                hideRefreshing()
                onErrorInConnection({
                    updateRecyclerView()
                })
//                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        Log.wtf("staretd", "hete")
        updateRecyclerView()
        super.onStart()
    }

    private fun showSelectLocationDialog() {
        MaterialDialog.Builder(activity!!)
                .title("Welcome to VHire")
                .items(R.array.user_type_choices)
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(-1) { dialog, view, which, text ->
                    showLocationDialog()
                    true
                }
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                presenter.saveLocationPreferences(data, activity)

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(activity!!, data!!)

                Log.i("TAG", status.statusMessage)

            } else if (resultCode == RESULT_CANCELED) {
                showToast("You need to select your preferred location before continuing", "error")
                showLocationDialog();
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.edit_location, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.reselect_location -> showLocationDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLocationDialog() {
        val autocompleteFilter = AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_STREET_ADDRESS)
                .setCountry("NG")
                .build()
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(autocompleteFilter)
                    .build(activity!!)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            showToast("Upgrade google play services")
        } catch (e: GooglePlayServicesNotAvailableException) {
            showToast("You need to install google play services")
        }

    }

    override fun UpdateToolbarText(text: String) {
        toolbar.subtitle = text
    }

    override fun initView() {
        val hasChosenAimInLife = Prefs.with(activity!!).readBoolean("has_chosen_aim_in_life")

        (activity!! as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.inflateMenu(R.menu.edit_location)

        if (!hasChosenAimInLife) {
            toolbar.subtitle = ""
            showSelectLocationDialog()
        } else {
            toolbar.subtitle = Prefs.with(activity!!).read("address")

        }
        refreshLayout.setOnRefreshListener { updateRecyclerView() }

        categoriesListRecyclerView.addOnItemTouchListener(RecyclerTouchListener(
                activity,
                categoriesListRecyclerView,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(context, "Clicked " + position.toString(), Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, CategoriesActivity::class.java)
                        val bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putString("categories", Gson().toJson(categories))
                        intent.putExtras(bundle)
                        startActivity(intent)

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                }
        ))

    }

    override fun showRefreshing() {
        refreshLayout.isRefreshing = true
    }

    override fun hideRefreshing() {
        refreshLayout.isRefreshing = false
    }

    companion object {

        private val NUM_COL = 3

        fun newInstance(): SearchFragment {

            return SearchFragment()
        }
    }
}

