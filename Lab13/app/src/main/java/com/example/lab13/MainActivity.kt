package com.example.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val msg = intent.extras?.getString("msg") ?: "未知訊息"
            findViewById<TextView>(R.id.tvMsg).text = msg
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupWindowInsets()
        setupButtons()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupButtons() {
        val buttons = mapOf(
            R.id.btnMusic to "music",
            R.id.btnNew to "new",
            R.id.btnSport to "sport"
        )

        buttons.forEach { (id, channel) ->
            findViewById<Button>(id).setOnClickListener {
                registerChannel(channel)
            }
        }
    }

    private fun registerChannel(channel: String) {
        val intentFilter = IntentFilter(channel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, intentFilter)
        }

        val intent = Intent(this, MyService::class.java).apply {
            putExtra("channel", channel)
        }
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
