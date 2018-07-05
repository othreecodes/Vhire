package ng.apk.vhire.ui.servicedetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotterknife.bindView
import ng.apk.vhire.R
import ng.apk.vhire.models.ServiceExtra

class ExtrasAdapter(val extras:List<ServiceExtra>, val context:Context): RecyclerView.Adapter<ExtrasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_service_extra, parent, false)
        Log.wtf("SES","oncreate")

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.wtf("SES","onbindviewgolhfdr ${extras.size}")

        return extras.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serviceExtra = extras[position]
        holder.extraPrice.text = serviceExtra.price.toString()
        holder.extraName.text = serviceExtra.name.toString()
        holder.extraDescription.text = serviceExtra.description.toString()
        Log.wtf("SES","onbindviewgolhfdr")



    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val extraPrice:TextView by bindView(R.id.extra_price)
        val extraName:TextView by bindView(R.id.extra_name)
        val extraDescription:TextView by bindView(R.id.extra_description)
    }
}