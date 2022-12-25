package com.rezazali.counterfeature

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.rezazali.counterfeature.databinding.ActivityShowWebBinding

class ShowWebActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowWebBinding
    lateinit var bundle:Bundle
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras!!

        val url:String = bundle.getString("url","")

        val title:String = bundle.getString("title","")
        binding.txtTitle.text = title

        binding.myWeb.requestFocus()
        binding.myWeb.settings.setGeolocationEnabled(true)
        binding.myWeb.isSoundEffectsEnabled = true
        binding.myWeb.settings.javaScriptEnabled = true

        binding.myWeb.webViewClient = WebViewClient()


/*
        binding.myWeb.loadUrl("javascript:(function() { " +
                "document.getElementsByClassName(py-2 bg-white dark:bg-black border-b border-gray-200 dark:border-gray-800)[1].style.display='none'; " +
                "})()");*/

        binding.myWeb.loadUrl(url)
        binding.imgBack.setOnClickListener {
            finish()
        }

/*        val url = "https://games.cdn.famobi.com/html5games/p/pizza-ninja-3/v090/?fg_domain=play.famobi.com&fg_aid=A1000-100&fg_uid=d9679933-b9f4-46f7-b40f-b28308c58eb3&fg_pid=5a106c0b-28b5-48e2-ab01-ce747dda340f&fg_beat=555&original_ref=https%3A%2F%2Fhtml5games.com%2F"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)*/

  /*      binding.myWeb.getSettings().setJavaScriptEnabled(true);
        binding.myWeb.getSettings().setBuiltInZoomControls(true);
        binding.myWeb.getSettings().setBuiltInZoomControls(true);
        binding.myWeb.setWebViewClient(WebViewClient());
        binding.myWeb.loadUrl("https://games.cdn.famobi.com/html5games/p/pizza-ninja-3/v090/?fg_domain=play.famobi.com&fg_aid=A1000-100&fg_uid=d9679933-b9f4-46f7-b40f-b28308c58eb3&fg_pid=5a106c0b-28b5-48e2-ab01-ce747dda340f&fg_beat=555&original_ref=https%3A%2F%2Fhtml5games.com%2F");*/


    }
}