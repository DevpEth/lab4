package com.codepath.campgrounds

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.campgrounds.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "CampgroundsMain/"
private val PARKS_API_KEY = BuildConfig.API_KEY
private val CAMPGROUNDS_URL = "https://developer.nps.gov/api/v1/campgrounds?api_key=${PARKS_API_KEY}"

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val campgrounds = mutableListOf<Campground>()
    private lateinit var adapter: CampgroundAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CampgroundAdapter(this, campgrounds)
        binding.campgrounds.layoutManager = LinearLayoutManager(this)
        binding.campgrounds.adapter = adapter
        binding.campgrounds.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        fetchCampgrounds()
    }

    private fun fetchCampgrounds() {
        val client = AsyncHttpClient()
        client.get(CAMPGROUNDS_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "Failed to fetch campgrounds: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched campgrounds: $json")
                try {
                    val jsonStr = json.jsonObject.toString()
                    val response = createJson().decodeFromString<CampgroundResponse>(jsonStr)
                    campgrounds.clear()
                    campgrounds.addAll(response.data)
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}
