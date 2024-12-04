package com.example.lab12

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        // 使用 Coroutine 執行耗時任務
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(3000) // 延遲三秒
                // 宣告 Intent，從 MyService 啟動 SecActivity
                val intent = Intent(this@MyService, SecActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK // 加入 Flag 表示產生新 Activity 實例
                }
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 返回 START_NOT_STICKY 表示 Service 結束後不會自動重啟
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null
}
