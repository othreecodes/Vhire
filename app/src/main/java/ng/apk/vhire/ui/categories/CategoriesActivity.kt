package ng.apk.vhire.ui.categories

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import ng.apk.vhire.R
import ng.apk.vhire.models.JobCategory

import kotterknife.bindView
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Service
import ng.apk.vhire.ui.base.BaseActivity
import ng.apk.vhire.ui.servicedetail.ServiceDetailActivity
import ng.apk.vhire.utils.RecyclerTouchListener

class CategoriesActivity : BaseActivity(), CategoriesContract.View {
    override fun updateServiceList(services: List<Service>) {
        servicesList = services
        mAdapter.services = servicesList

        mAdapter.notifyDataSetChanged()

        Log.e("rrrrr", "ddddd")
    }

    override lateinit var presenter: CategoriesContract.Presenter



    private var toolbar: Toolbar? = null
    lateinit var servicesList: List<Service>

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var mAdapter: ServicesAdapter
    private var categories: List<JobCategory>? = null
    internal var position = 0
    val recyclerView: RecyclerView by bindView(R.id.services_recycler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        CategoriesPresenter(this,http = Http(this))
        val b = intent.extras
        if (b != null) {
            this.categories = Gson().fromJson<List<JobCategory>>(b.getString("categories"), object : TypeToken<List<JobCategory>>() {

            }.type)
            this.position = b.getInt("position")
         }
        presenter.start()



    }

    override fun onStart() {
        super.onStart()

    }


    override fun initView() {
        val category = this.categories!![this.position]
        toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar!!.title = category!!.name

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        mAdapter = ServicesAdapter(this)
        recyclerView.adapter = mAdapter

        recyclerView.addOnItemTouchListener(RecyclerTouchListener(
                this, recyclerView, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(this@CategoriesActivity, ServiceDetailActivity::class.java)
//
                intent.putExtra("service", servicesList[position])
                startActivity(intent)

            }

            override fun onLongClick(view: View?, position: Int) {

            }

        }
        ))

        presenter.fetchServices(category!!.id)


    }


}
