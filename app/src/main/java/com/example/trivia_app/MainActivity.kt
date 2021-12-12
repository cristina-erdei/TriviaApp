package com.example.trivia_app

import adaptors.QuestionRecycleViewAdaptor
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trivia_app.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.httpGet
import model.Question
import model.QuestionList


class MainActivity : AppCompatActivity() {
    lateinit var adaptor: QuestionRecycleViewAdaptor
    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.downloadButton.setOnClickListener {
            val inputString = binding.numberInput.text.toString()
            if (inputString == "") {
                binding.numberInput.error = "This field cannot be empty"
                return@setOnClickListener
            }
            val number = inputString.toInt()
            handleDownload(number)
        }

        setContentView(binding.root)
        //TODO set proper data source
        adaptor = QuestionRecycleViewAdaptor(QuestionList.getTestList())
        val layoutManager = LinearLayoutManager(this)

        recyclerView = binding.questionsReciclerView
        progress = binding.progressbar
        recyclerView.adapter = adaptor
        recyclerView.layoutManager = layoutManager
    }

    private fun handleDownload(number: Int) {
        progress.visibility = View.VISIBLE

        println("download: $number")
        val url = "https://jservice.io/api/random"

        url.httpGet(listOf("count" to number)).responseString { request, response, result ->
            println(response.responseMessage)
                println(" hristos " + result.component1())
                println(" code " + response.statusCode)

            if ( response.statusCode == 200) {
                recyclerView.visibility = View.GONE
                val qs = result.component1()?.let { Question.fromJson(it) }
                println(" dumnezo " + qs)
                adaptor.updateData(qs ?: listOf())

            }
                progress.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_add -> {
                val next = Intent(this, AddQuestionActivity::class.java)
                startActivity(next)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}