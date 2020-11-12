package com.example.httpjsondemo1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.stations_row.view.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // appwelcomeText.text = "Http JSON Demo"

        recyclerStationList.setBackgroundColor(Color.YELLOW)


        val stationList = listOf(

            Bike(25, "Street 5", 10),
            Bike(26, "Street 2", 12),
            Bike(27, "Street 3", 13)

        )

        recyclerStationList.layoutManager = LinearLayoutManager(this)

       // recyclerStationList.adapter = StationListAdapter(stationList)
        getJson()
    }

    private fun getJson() {
        //   TODO("Not yet implemented")
        Log.i("JSON", "GET JSON IS CALLED")

        //  val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        var url =
            "https://api.jcdecaux.com/vls/v1/stations?contract=dublin&apiKey=013c96fda1ac6937698c8402e42b0c31f2cc081e"

        //Create a request object

        val request = Request.Builder().url(url).build()

        //Create a client

        val client = OkHttpClient()

        //Use client object to work with request object

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // TODO("Not yet implemented")
                Log.i("JSON", "JSON HTTP CALL FAILED")
            }

            override fun onResponse(call: Call, response: Response) {
                // TODO("Not yet implemented")
                Log.i("JSON", "JSON HTTP CALL SUCCEEDED")
                val body = response?.body?.string()
                //  println("json loading" + body)
                Log.i("JSON", body)

                //body


                var jsonBody = "{\"stations\": " + body + "}"
                Log.i("JSON", jsonBody)

                val gson = GsonBuilder().create()
                var stationList = gson.fromJson(jsonBody, Stations::class.java)

                Log.i("JSON", stationList.stations[0].name)

                runOnUiThread {
                    recyclerStationList.adapter = StationListAdapter(stationList.stations)
                }
            }
        })

    }
}

class StationListAdapter(val stations: List<Bike>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.stations_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return stations.size;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.stationName.text = stations[position].name
        holder.itemView.bikesAvailable.text = stations[position].available_bikes.toString()
    }

}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}
