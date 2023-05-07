package jp.techacademy.yuya.ozawa.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.techacademy.yuya.ozawa.apiapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.loadUrl(intent.getStringExtra(KEY_URL).toString())

    }

    companion object {
        private const val KEY_URL = "key_url"
        private const val KEY_ID = "key_id"
        private const val KEY_IMAGE_URL = "key_image_url"
        private const val KEY_NAME = "key_name"

        fun start(activity: Activity, url: String, id: String = "", imageUrl: String = "", name: String = "") {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_ID, id)
            intent.putExtra(KEY_IMAGE_URL, imageUrl)
            intent.putExtra(KEY_NAME, name)
            activity.startActivity(intent)
        }
    }
    }
