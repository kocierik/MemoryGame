package com.example.memory

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
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
        val TextNumber: EditText  = findViewById(R.id.userNumber)
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
    @SuppressLint("SetTextI18n")
    private fun startGame(){
        Toast.makeText(this, "The game is start!", Toast.LENGTH_SHORT).show()
        var gameNumberList = mutableListOf<String>()
        var lengthGameNumberList = mutableListOf<Int>()
        var userNumberList = mutableListOf<String>()
        var totalTime = 6000L
        score = 0
        remove = ""
        i = 0
        k=0
        txtScore.text = "score\n$score"
        userNumber.text= null

        btnStart.visibility = View.INVISIBLE

        object : CountDownTimer(totalTime, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                addGameNumber(lengthGameNumberList,gameNumberList)
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {

                txtNumber.text =""
                userNumber.visibility = View.VISIBLE
                txtScore.visibility = View.VISIBLE
                btnStart.visibility = View.INVISIBLE
                var number = ""

                userNumber.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(s: CharSequence,start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                        checkNumber(gameNumberList, userNumberList)
                        txtScore.text = "score\n$score"
                        }
                })
            }
        }.start()
    }

    private fun addGameNumber(lengthGameNumberList: MutableList<Int>, gameNumberList: MutableList<String>){
        txtNumber.text =  (10..100).random().toString()
        lengthGameNumberList.add(txtNumber.text.length)
        gameNumberList.add(txtNumber.text as String)
    }

    var score = 0
    var i = 0
    var bool = false
    var k = 0
    var remove = ""
    @SuppressLint("SetTextI18n", "ResourceType")
    private fun checkNumber(gameNumberList: MutableList<String>, userNumberList: MutableList<String>) {
        if (userNumber.text.length == gameNumberList.max()?.length) {
            userNumberList.add(userNumber.text.toString())
            for (x in gameNumberList) {
                if (userNumberList[i] == gameNumberList[k] && userNumberList[i].length == gameNumberList[k].length) {
                    score += 1
                    bool = true
                }
                k +=1
            }

            i += 1
            k = 0

            userNumber.text = null
            if(!bool){
                txtNumber.text = "You Lose"
                userNumber.visibility = View.INVISIBLE
                btnStart.visibility = View.VISIBLE
                i=0
                gameNumberList.clear()
                userNumberList.clear()
            }
            bool = false
        }
    }
}

