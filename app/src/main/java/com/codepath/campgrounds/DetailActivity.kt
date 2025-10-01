package com.codepath.campgrounds

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "CampgroundDetailActivity"
const val CAMPGROUND_EXTRA = "CAMPGROUND_EXTRA"

class DetailActivity : AppCompatActivity() {
    private lateinit var campgroundNameTV: TextView
    private lateinit var campgroundDescriptionTV: TextView
    private lateinit var campgroundLatLongTV: TextView
    private lateinit var campgroundImageIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        campgroundNameTV = findViewById(R.id.campgroundName)
        campgroundDescriptionTV = findViewById(R.id.campgroundDescription)
        campgroundLatLongTV = findViewById(R.id.campgroundLocation)
        campgroundImageIV = findViewById(R.id.campgroundImage)

        val campground = intent.getParcelableExtra<Campground>(CAMPGROUND_EXTRA)

        campground?.let {
            campgroundNameTV.text = it.name ?: "No Name"
            campgroundDescriptionTV.text = it.description ?: "No Description"
            campgroundLatLongTV.text = "Lat: ${it.latitude ?: "N/A"}, Lon: ${it.longitude ?: "N/A"}"
            val imageUrl = it.images?.firstOrNull()?.url
            Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campgroundImageIV)
        }
    }
}
