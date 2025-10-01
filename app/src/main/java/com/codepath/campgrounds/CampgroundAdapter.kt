package com.codepath.campgrounds

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CampgroundAdapter(
    private val context: Context,
    private val campgrounds: List<Campground>
) : RecyclerView.Adapter<CampgroundAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_campground, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(campgrounds[position])
    }

    override fun getItemCount() = campgrounds.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val campgroundImage: ImageView = itemView.findViewById(R.id.campgroundImage)
        private val campgroundName: TextView = itemView.findViewById(R.id.campgroundName)
        private val campgroundLocation: TextView = itemView.findViewById(R.id.campgroundLocation)
        private val campgroundDescription: TextView = itemView.findViewById(R.id.campgroundDescription)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(campground: Campground) {
            campgroundName.text = campground.name ?: "No Name"
            campgroundLocation.text = "Lat: ${campground.latitude ?: "N/A"}, Lon: ${campground.longitude ?: "N/A"}"
            campgroundDescription.text = campground.description ?: "No Description"
            val imageUrl = campground.images?.firstOrNull()?.url
            Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campgroundImage)
        }

        override fun onClick(v: View?) {
            val campground = campgrounds[adapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(CAMPGROUND_EXTRA, campground)
            context.startActivity(intent)
        }
    }
}
