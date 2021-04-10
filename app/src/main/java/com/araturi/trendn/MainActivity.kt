package com.araturi.trendn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    var gson = Gson()
    var defaultFilteredText = "afropunk"

    // Fetch all post from trend-n api endpoints which stores its document in mongodb atlas
    fun fetchAllPost() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://trend-n.herokuapp.com/api/getAllPost"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var mListView = findViewById<ListView>(R.id.listView)
                val objectList = gson.fromJson(response, Array<FashionPost>::class.java).asList()

                var arrayAdapter: ArrayAdapter<*>
                arrayAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1, objectList
                )
                mListView.adapter = arrayAdapter

                mListView.setClickable(true)
                mListView.setOnItemClickListener(OnItemClickListener { arg0, arg1, position, arg3 ->
                    val o: Any = mListView.getItemAtPosition(position)
                    Log.d("Selected ", o.toString())
                    var intent:Intent = Intent(this,ShowDetails::class.java)
                    intent.putExtra("fashionPost", o as Serializable)
                    startActivity(intent)
                })

            },
            Response.ErrorListener { Log.d("HTTP Requst", "that didnt work ") })

        queue.add(stringRequest)

    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchAllPost()

        val spinner = findViewById<Spinner>(R.id.spinner)
        val items = arrayOf("afropunk","bohemian","Formal","Emo")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            items
        )
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                arg2: Int,
                arg3: Long
            ) {
                val items = spinner.selectedItem.toString()
                defaultFilteredText = items
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

    }

    fun fetchPostWithFilter(view : View) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://trend-n.herokuapp.com/api/filter/" + defaultFilteredText

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var mListView = findViewById<ListView>(R.id.listView)
                val objectList = gson.fromJson(response, Array<FashionPost>::class.java).asList()

                var arrayAdapter: ArrayAdapter<*>
                arrayAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1, objectList
                )
                mListView.adapter = arrayAdapter

                mListView.setClickable(true)
                mListView.setOnItemClickListener(OnItemClickListener { arg0, arg1, position, arg3 ->
                    val fashionPost: Any = mListView.getItemAtPosition(position)

                    Log.d("Selected ", fashionPost.toString())
                    var intent:Intent = Intent(this,ShowDetails::class.java)
                    intent.putExtra("fashionPost", fashionPost as Serializable)
                    startActivity(intent)
                })

            },
            Response.ErrorListener { Log.d("HTTP Requst", "that didnt work ") })

        queue.add(stringRequest)

    }

}
