package com.example.instop

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GoalSettingActivity : AppCompatActivity() {

    private lateinit var hourPicker: NumberPicker
    private lateinit var minutePicker: NumberPicker
    private lateinit var intervalPicker: NumberPicker
    private lateinit var btnSaveGoal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        hourPicker = findViewById(R.id.hourPicker)
        minutePicker = findViewById(R.id.minutePicker)
        intervalPicker = findViewById(R.id.intervalPicker)
        btnSaveGoal = findViewById(R.id.btnSaveGoal)

        // NumberPicker 범위 설정
        hourPicker.minValue = 0
        hourPicker.maxValue = 23

        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        intervalPicker.minValue = 1
        intervalPicker.maxValue = 60

        // 버튼 클릭 시 목표 저장
        btnSaveGoal.setOnClickListener {
            val hours = hourPicker.value
            val minutes = minutePicker.value
            val interval = intervalPicker.value
            val totalMinutes = hours * 60 + minutes

            if (totalMinutes == 0) {
                Toast.makeText(this, "0분은 설정할 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // SharedPreferences에 저장
            val prefs = getSharedPreferences("GoalPrefs", Context.MODE_PRIVATE)
            with(prefs.edit()) {
                putInt("target_minutes", totalMinutes)
                putInt("notify_interval", interval)
                apply()
            }

            Toast.makeText(
                this,
                "목표 시간: ${totalMinutes}분, 알림 주기: ${interval}분",
                Toast.LENGTH_SHORT
            ).show()

            // 현재 액티비티 종료 (MainActivity로 돌아감)
            finish()
        }
    }
}
