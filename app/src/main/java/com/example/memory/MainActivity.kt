package com.example.memory

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    }

    private fun startGame(){
        Toast.makeText(this, "The game is start!", Toast.LENGTH_SHORT).show()
        btnStart.visibility = View.INVISIBLE
        val gameNumberList = mutableListOf<String>()
        val lengthGameNumberList = mutableListOf<Int>()
        val userNumberList = mutableListOf<String>()
        var totalTime = 4000L


        userNumber.visibility = View.INVISIBLE


        object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                addGameNumber(lengthGameNumberList,gameNumberList)
                totalTime +=1000

            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                txtNumber.text =""
                userNumber.visibility = View.VISIBLE
                txtScore.visibility = View.VISIBLE
                var number = ""

                userNumber.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {

                    }

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    )   {
                        checkNumber(gameNumberList, userNumberList, i, score).toString()
                        txtScore.text = "score: $score"
                        }
                })

            }
        }.start()
    }

    private fun addGameNumber(lengthGameNumberList: MutableList<Int>, gameNumberList: MutableList<String>){
        txtNumber.text =  (1..100).random().toString()
        lengthGameNumberList.add(txtNumber.text.length)
        gameNumberList.add(txtNumber.text as String)
    }

    var score = 0
    var i = 0

    @SuppressLint("SetTextI18n")
    private fun checkNumber(
        gameNumberList: MutableList<String>, userNumberList: MutableList<String>, i:Int, score: Int): Int {
        for (x in gameNumberList){
            if (userNumber.text.toString() == gameNumberList[i]){
                this.score +=1
                userNumber.text = null
                this.i +=1
            }
            else if(userNumber.text.length == gameNumberList[i].length){
                txtNumber.text = "You Lose"
                userNumber.visibility = View.INVISIBLE
            }
        }
        return score
    }
}

