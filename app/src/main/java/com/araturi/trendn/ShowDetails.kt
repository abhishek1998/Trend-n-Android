package com.araturi.trendn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.budiyev.android.imageloader.ImageLoader

class ShowDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        var getFashionPost = intent.extras?.get("fashionPost") as FashionPost
        Log.d("ShowDetail", getFashionPost.toString())

        var imgView = findViewById<ImageView>(R.id.imageView)
        ImageLoader.with(this).from("https://trend-n.herokuapp.com/api/getImg/" + getFashionPost.img)
            .load(imgView);


        var priceTextView = findViewById<TextView>(R.id.price)
        priceTextView.text = "CAD " +  getFashionPost.price.toString();

        var styleTextView = findViewById<TextView>(R.id.style)
        styleTextView.text = getFashionPost.style;

        var brandTextView = findViewById<TextView>(R.id.brand)
        brandTextView.text = getFashionPost.brand;

    }

    fun launchHomeScreen(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}