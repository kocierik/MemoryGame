package com.example.memory

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.btnStart)
        button.setOnClickListener { startGame() }
        val txtGame: TextView = findViewById(R.id.txtNumber)
        val TextNumber: EditText = findViewById(R.id.userNumber)
        val score: TextView = findViewById(R.id.txtScore)
        txtScore.visibility = View.INVISIBLE
        val image: ImageView = findViewById(R.id.image)
        userNumber.visibility = View.INVISIBLE

        val clkRotate = AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_clockwise
        )
        image.startAnimation(clkRotate)
    }
    var timer: CountDownTimer? = null
    var score = 0
    var totalTime = 1000L
    var game = true
    var gameNumberList = mutableListOf<String>()
    @SuppressLint("SetTextI18n")
    private fun startGame(){
        score = 0
        txtScore.text = "score\n $score"
        btnStart.visibility = View.INVISIBLE
        Toast.makeText(this, "The game is start!", Toast.LENGTH_SHORT).show()
        getRandomNumber()
    }
    
    private fun onFinishTimer(gameNumberList: MutableList<String>) {
        game = true
        txtNumber.text = ""
        btnStart.visibility = View.INVISIBLE
        userNumber.visibility = View.VISIBLE
        txtScore.visibility = View.VISIBLE

        userNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                txtScore.text = "score\n$score"
                Log.d("CHECK", "LISTA $gameNumberList")
                checkNumber()
            }
        })
    }

    private fun checkNumber() {
        Log.d("PROVA", "LISTA $gameNumberList")
        if (userNumber.text.length == gameNumberList.max()?.length && gameNumberList.isNotEmpty()) {
            for ((i, x) in gameNumberList.withIndex()) {
                game = false
                if (userNumber.text.toString() == gameNumberList[i] && userNumber.text.toString().length == gameNumberList[i].length) {
                    game = true
                    score++
                    gameNumberList.removeAt(i)
                    checkEmptyList()
                    userNumber.text = null
                    break
                }
            }
        }
        checkLose()
    }
    private fun getRandomNumber(){
        userNumber.visibility = View.INVISIBLE
        timer = object: CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtNumber.text = (10..100).random().toString()
                gameNumberList.add(txtNumber.text as String)
            }
            override fun onFinish() {
                onFinishTimer(gameNumberList)
                userNumber.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun checkEmptyList(){
        if(gameNumberList.isEmpty()) {
            timer?.cancel()
            timer = null
            totalTime += 1000
            getRandomNumber()
        }
    }
    private fun checkLose(){
        if(!game) {
            Log.d("HAI PERSO", "ok")
            txtNumber.text = getString(R.string.txt_lose)
            userNumber.visibility = View.INVISIBLE
            btnStart.visibility = View.VISIBLE
            gameReset()
        }
    }
    private fun gameReset(){
        totalTime = 1000L
        game = true
        timer?.cancel()
        timer = null
        gameNumberList.clear()
        userNumber.text = null
    }
}

