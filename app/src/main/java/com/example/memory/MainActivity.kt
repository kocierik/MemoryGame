package com.example.memory

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
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
        val button0: Button = findViewById(R.id.button0)
        button0.setOnClickListener { keyboardInput(button0) }
        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener { keyboardInput(button1) }
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener { keyboardInput(button2) }
        val button3: Button = findViewById(R.id.button3)
        button3.setOnClickListener { keyboardInput(button3) }
        val button4: Button = findViewById(R.id.button4)
        button4.setOnClickListener { keyboardInput(button4) }
        val button5: Button = findViewById(R.id.button5)
        button5.setOnClickListener { keyboardInput(button5) }
        val button6: Button = findViewById(R.id.button6)
        button6.setOnClickListener { keyboardInput(button6) }
        val button7: Button = findViewById(R.id.button7)
        button7.setOnClickListener { keyboardInput(button7) }
        val button8: Button = findViewById(R.id.button8)
        button8.setOnClickListener { keyboardInput(button8) }
        val button9: Button = findViewById(R.id.button9)
        button9.setOnClickListener { keyboardInput(button9) }
        val delete: Button = findViewById(R.id.delete)
        delete.setOnClickListener { deleteNumber() }
        button0.background = borderDrawable(10, Color.WHITE)
        button1.background = borderDrawable(10, Color.BLUE)
        button2.background = borderDrawable(10, Color.BLUE)
        button3.background = borderDrawable(10, Color.BLUE)
        button4.background = borderDrawable(10, Color.BLUE)
        button5.background = borderDrawable(10, Color.BLUE)
        button6.background = borderDrawable(10, Color.BLUE)
        button7.background = borderDrawable(10, Color.BLUE)
        button8.background = borderDrawable(10, Color.BLUE)
        button9.background = borderDrawable(10, Color.BLUE)
        delete.background = borderDrawable(10, Color.RED)
        userNumber.isEnabled = false;
        findViewById<LinearLayout>(R.id.firstNumber).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.secondNumber).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.thirdNumber).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.fourNumber).visibility = View.INVISIBLE
        val txtGame: TextView = findViewById(R.id.txtNumber)
        val TextNumber: EditText = findViewById(R.id.userNumber)
        val score: TextView = findViewById(R.id.txtScore)
        txtScore.visibility = View.INVISIBLE
        val image: ImageView = findViewById(R.id.image)
        userNumber.visibility = View.INVISIBLE
        val clkRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
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

    private fun borderDrawable(
        width: Int = 10, // border width in pixels
        color: Int = Color.BLACK, // border color
        bgColor: Int = Color.TRANSPARENT // view background color
    ): Drawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setStroke(width, color)
            setColor(bgColor)
        }
    }

    private fun keyboardInput(btnClicked: Button){
        var txt = "${userNumber.text}${btnClicked.text}"
        userNumber.setText(txt)
        userNumber.setSelection((userNumber.text.length))
    }

    private fun onFinishTimer(gameNumberList: MutableList<String>) {
        game = true
        txtNumber.text = ""
        btnStart.visibility = View.INVISIBLE
        userNumber.visibility = View.VISIBLE
        txtScore.visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.firstNumber).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.secondNumber).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.thirdNumber).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.fourNumber).visibility = View.VISIBLE

        userNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                txtScore.text = "score\n$score"
                checkNumber()
            }
        })
    }

    private fun deleteNumber(){
        var number = userNumber.text.toString()
        //number = number.dropLast()
        if(number.isNotEmpty()){
        number = number.substring(0, number.length - 1);
        userNumber.setText(number)
        }else{
            Toast.makeText(this, "There aren't numbers!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkNumber() {
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
            txtNumber.text = "YOU LOSE"
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
        findViewById<LinearLayout>(R.id.firstNumber).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.secondNumber).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.thirdNumber).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.fourNumber).visibility = View.INVISIBLE
    }
}

