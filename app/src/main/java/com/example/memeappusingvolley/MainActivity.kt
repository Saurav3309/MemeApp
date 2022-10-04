package com.example.memeappusingvolley

import android.content.Intent
import android.media.session.PlaybackState
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    lateinit var memeImage: ImageView
    var image: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        memeImage = findViewById(R.id.memeImage)

        loadMeme()

    }

    fun loadMeme() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                image = it.getString("url")
                Glide.with(this).load(image).into(memeImage)

            },
            Response.ErrorListener {
                Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show()

            })
        queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "check it out $image")
        val chooser = Intent.createChooser(intent, "sharing")
        startActivity(chooser)
    }

    fun nextMeme(view: View) {
        loadMeme()
    }
}
