package com.app.astratask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.EditText


class pomodoro : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var studyEditText: EditText
    private lateinit var breakEditText: EditText
    private var isTimerRunning = false
    private var currentTimeInMillis: Long = 0
    private var isStudySession = true
    private var completedCycles = 0
    private val maxCycles = 3

    private var studySessionDuration = 25 * 60 * 1000L // Default: 25 minutes
    private var breakDuration = 5 * 60 * 1000L // Default: 5 minutes

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)
        studyEditText = findViewById(R.id.studyEditText)
        breakEditText = findViewById(R.id.breakEditText)

        startButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                val studyMinutes = studyEditText.text.toString().toIntOrNull()
                val breakMinutes = breakEditText.text.toString().toIntOrNull()

                if (studyMinutes != null && studyMinutes > 0 && breakMinutes != null && breakMinutes > 0) {
                    studySessionDuration = studyMinutes * 60 * 1000L
                    breakDuration = breakMinutes * 60 * 1000L
                    if (isStudySession) {
                        currentTimeInMillis = studySessionDuration
                    } else {
                        currentTimeInMillis = breakDuration
                    }
                    updateTimerText()
                    startTimer()
                } else {
                    // Manejar el caso en el que el usuario no ingresó valores válidos
                }
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (completedCycles < maxCycles) {
            countDownTimer = object : CountDownTimer(currentTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    currentTimeInMillis = millisUntilFinished
                    updateTimerText()
                }

                override fun onFinish() {
                    // Timer finished, handle break or next study session here
                    if (isStudySession) {
                        isStudySession = false
                        currentTimeInMillis = breakDuration
                    } else {
                        isStudySession = true
                        currentTimeInMillis = studySessionDuration
                        completedCycles++
                    }
                    updateTimerText()
                    startTimer()
                }
            }

            countDownTimer.start()
            isTimerRunning = true
            startButton.text = "Pause"
            resetButton.visibility = View.INVISIBLE
            studyEditText.isEnabled = false
            breakEditText.isEnabled = false
        } else {
            // El número máximo de ciclos se ha alcanzado
            // Puedes mostrar un mensaje o realizar una acción aquí
        }
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
        startButton.text = "Start"
        resetButton.visibility = View.VISIBLE
        studyEditText.isEnabled = true
        breakEditText.isEnabled = true
    }

    private fun resetTimer() {
        if (isStudySession) {
            currentTimeInMillis = studySessionDuration
        } else {
            currentTimeInMillis = breakDuration
        }
        updateTimerText()
        isTimerRunning = false
        startButton.text = "Start"
        resetButton.visibility = View.INVISIBLE
        studyEditText.isEnabled = true
        breakEditText.isEnabled = true
        completedCycles = 0 // Restablece el número de ciclos completados
    }

    private fun updateTimerText() {
        val minutes = (currentTimeInMillis / 1000) / 60
        val seconds = (currentTimeInMillis / 1000) % 60
        val timeText = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = timeText
    }
}







