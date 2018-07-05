package ng.apk.vhire.ui.servicedetail


import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.DatePicker
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_service_detail.*
import kotlinx.android.synthetic.main.content_service_detail.*
import ng.apk.vhire.MainActivity
import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Service
import ng.apk.vhire.ui.base.BaseActivity
import ng.apk.vhire.ui.placebooking.PlaceBookingActivity
import java.util.*


class ServiceDetailActivity : BaseActivity(), ServiceDetailContract.View, DatePickerDialog.OnDateSetListener {
    override fun updateExtrasList(service: Service?) {
        var extras = service!!.extras
        val mAdapter = ExtrasAdapter(extras, this)

        extrarecyclerView.adapter = mAdapter
        extrarecyclerView.layoutManager = LinearLayoutManager(this)
        //  extrarecyclerView.setBackgroundColor(resources.getColor(R.color.primary_dark_material_dark))
        mAdapter.notifyDataSetChanged()
        Toast.makeText(this, extras.size.toString(), Toast.LENGTH_LONG)
                .show()
//        extras.
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }

    val calendar: Calendar = Calendar.getInstance()
    override lateinit var presenter: ServiceDetailContract.Presenter

    lateinit var service: Service
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)
        setSupportActionBar(toolbar)

        ServiceDetailPresenter(this, Http(this))

        val bundle = intent.extras

        if (bundle != null) {
            service = bundle.getParcelable<Service>("service") as Service
        }
        presenter.start()

    }

    override fun checkIfLoggedInAndRedirect(): Boolean {

        return Prefs.with(this).contains("token")

    }

    override fun initView() {
        toolbar_layout.title = service.name
        text_content.text = service.description

        Glide.with(this).load(service.image).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    toolbar_layout.background = resource

            }

        })

        presenter.fetchServiceExtras(service.id)

        btn_book.setOnClickListener {
            if (!checkIfLoggedInAndRedirect()) {
                showToast("You need to be logged in to do this")
                val intent = Intent(this@ServiceDetailActivity, MainActivity::class.java)
                intent.putExtra("profile", true)
                startActivity(intent)
            } else {
                val intent = Intent(this@ServiceDetailActivity, PlaceBookingActivity::class.java)

                intent.putExtra("service", service)
                startActivity(intent)
            }

        }
    }


}
