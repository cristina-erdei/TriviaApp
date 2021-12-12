package com.example.trivia_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.example.trivia_app.databinding.ActivityAddQuestionBinding
import com.google.android.material.textfield.TextInputEditText
import model.Category
import model.Question
import model.QuestionList
import java.time.LocalDateTime
import java.util.*

class AddQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddQuestionBinding


    override fun onBackPressed() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        val yesButton = dialog.findViewById(R.id.yesButton) as Button
        val noButton = dialog.findViewById(R.id.noButton) as Button

        yesButton.setOnClickListener {
            dialog.dismiss()

            var isError = false

            println(findViewById<EditText>(R.id.questionField).text)
            println(findViewById<EditText>(R.id.answerField).text)
            println(findViewById<EditText>(R.id.valueField).text)
            println(findViewById<Spinner>(R.id.categorySpinner).selectedItem)

            if (findViewById<EditText>(R.id.questionField).text.length < 5) {
                findViewById<EditText>(R.id.questionField).error = "Question should be longer than 5 characters."
                isError = true
            }
            if (findViewById<EditText>(R.id.answerField).text.length < 5) {
                findViewById<EditText>(R.id.answerField).error = "Answer should be longer than 5 characters."
                isError = true
            }
            if (findViewById<EditText>(R.id.valueField).text.toString()
                    .toIntOrNull() == null || findViewById<EditText>(R.id.valueField).text.toString()
                    .toInt() < 50 || findViewById<EditText>(R.id.valueField).text.toString().toInt() > 150
            ) {
                findViewById<EditText>(R.id.valueField).error = "Value should be between 50 and 150"
                isError = true
            }

            if (isError) {
                return@setOnClickListener
            }

            val q = Question(
                id = 1,
                answer = findViewById<EditText>(R.id.answerField).text.toString(),
                question = findViewById<EditText>(R.id.questionField).text.toString(),
                value = findViewById<EditText>(R.id.valueField).text.toString().toLong(),
                airdate = LocalDateTime.now().toString(),
                category = Category(findViewById<Spinner>(R.id.categorySpinner).selectedItem.toString())
            )

            QuestionList.questions.add(q)

            super.onBackPressed()
        }
        noButton.setOnClickListener {
            dialog.dismiss()
            super.onBackPressed()
        }

        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddQuestionBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_question)
        findViewById<Button>(R.id.button).setOnClickListener {
            println(findViewById<EditText>(R.id.questionField).text)
            println(findViewById<EditText>(R.id.answerField).text)
            println(findViewById<EditText>(R.id.valueField).text)

        }
//        binding.button.setOnClickListener {
//            println(binding.questionField.text)
//            println(binding.answerField.text)
//            println(binding.valueField.text)
//            println(binding.categorySpinner.selectedItem)
//        }
    }
}