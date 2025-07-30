//package com.example.instop
//
//import android.content.Context
//import android.content.Intent
//import android.provider.Settings
//import android.os.Bundle
//import android.widget.Button
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var usageText: TextView
////    private val targetMinutes = 30 // 목표 시간 (분)
//    private val targetMinutes = 1
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        usageText = findViewById(R.id.usageText)
//
//        // 권한이 없으면 사용 접근 설정으로 이동
//        if (!hasUsageStatsPermission()) {
//            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
//        }
//
//        // 사용 시간 측정
//        val usageMillis = UsageStatsHelper.getInstagramUsageToday(this)
//        val usageMinutes = usageMillis / 1000 / 60
//        usageText.text = "오늘 인스타그램 사용 시간: ${usageMinutes}분"
//
//        if (usageMinutes >= targetMinutes) {
//            NotificationHelper.showUsageLimitNotification(this)
//        }
//    }
//
//    private fun hasUsageStatsPermission(): Boolean {
//        val appOps = getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
//        val mode = appOps.checkOpNoThrow(
//            "android:get_usage_stats",
//            android.os.Process.myUid(), packageName
//        )
//        return mode == android.app.AppOpsManager.MODE_ALLOWED
//    }
//}

package com.example.instop

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.app.AppOpsManager


class MainActivity : AppCompatActivity() {

    private lateinit var usageText: TextView
    private lateinit var brainImage: ImageView
    private lateinit var goalText: TextView
    private lateinit var setGoalButton: Button
    private lateinit var openGroupButton: Button

    private val targetMinutes = 30  // 목표 시간
//    private val targetMinutes = 1  // 테스트용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usageText = findViewById(R.id.usageText)
        brainImage = findViewById(R.id.brainImage)
        goalText = findViewById(R.id.goalText)
        setGoalButton = findViewById(R.id.setGoalButton)
        openGroupButton = findViewById(R.id.openGroupButton)

        // 목표 텍스트 표시
        goalText.text = "하루 목표 사용 시간 : ${targetMinutes}분"

        // 권한 확인
        if (!hasUsageStatsPermission()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        // 사용 시간 측정
        val usageMillis = UsageStatsHelper.getInstagramUsageToday(this)
        val usageMinutes = usageMillis / 1000 / 60
        usageText.text = "현재 사용 시간\n${usageMinutes}분"

        updateBrainImage(usageMinutes.toInt())

        // 알림 표시
        if (usageMinutes >= targetMinutes) {
            NotificationHelper.showUsageLimitNotification(this)
        }

        // 버튼 클릭 이벤트
        setGoalButton.setOnClickListener {
            // TODO: 목표 시간 설정 다이얼로그 띄우기
        }

        openGroupButton.setOnClickListener {
            // TODO: 모임방 화면으로 이동
        }
    }

    private fun updateBrainImage(minutes: Int) {
        val imageRes = when {
            minutes < 15 -> R.drawable.brain_happy
            minutes < 30 -> R.drawable.brain_neutral
            else -> R.drawable.brain_tired
        }
        brainImage.setImageResource(imageRes)
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = appOps.checkOpNoThrow(
            "android:get_usage_stats",
            android.os.Process.myUid(), packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
}
