package ng.apk.vhire.ui.placebooking

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import kotterknife.bindView
import ng.apk.vhire.R
import ng.apk.vhire.models.Session


class PlaceBookingAdapter(val sessions:List<Session>) : RecyclerView.Adapter<PlaceBookingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_placebooking_list, parent, false)
        Log.wtf("SES", "oncreate")

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = sessions[position]

        val word = "Booking on ${s.start} - from ${s.time} for ${s.no_of_hours} hours"
        holder.textview.setText(word)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textview: TextView by bindView(R.id.text)
    }
}