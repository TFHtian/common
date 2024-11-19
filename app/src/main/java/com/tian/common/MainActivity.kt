package com.tian.common

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tian.common.app.feature.keystore.KeyStoreActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).setOnClickListener {
            val intent = Intent(this,KeyStoreActivity::class.java)
            startActivity(intent)
        }
    }
}