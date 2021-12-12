package com.example.trivia_app

import adaptors.QuestionRecycleViewAdaptor
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trivia_app.databinding.ActivityMainBinding
import model.Question
import model.QuestionList
import okhttp3.*

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var adaptor: QuestionRecycleViewAdaptor
    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        mHandler = Handler(Looper.getMainLooper())

        binding.downloadButton.setOnClickListener {
            val inputString = binding.numberInput.text.toString()
            if (inputString == "") {
                binding.numberInput.error = "This field cannot be empty"
                return@setOnClickListener
            }
            val number = inputString.toInt()
            if (number < 1 || number > 100) {
                binding.numberInput.error = "The number must be between 1 and 100"
                return@setOnClickListener
            }
            handleDownload(number)
        }

        setContentView(binding.root)
        //TODO set proper data source
        adaptor = QuestionRecycleViewAdaptor()
        val layoutManager = LinearLayoutManager(this)

        recyclerView = binding.questionsReciclerView
        progress = binding.progressbar
        recyclerView.adapter = adaptor
        recyclerView.layoutManager = layoutManager
    }


    private fun handleDownload(number: Int) {
        progress.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        println("download: $number")
        val url = "https://jservice.io/api/random"


        val urlBuilder: HttpUrl.Builder = url.toHttpUrlOrNull()!!.newBuilder()
        urlBuilder.addQueryParameter("count", number.toString())

        val urll: String = urlBuilder.build().toString()


        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url(urll)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                if (response.code != 200) {
                    println(response)
                    return
                }

                val data = String(response.body?.bytes()!!)

                val questions = Question.fromJson(data)

                QuestionList.questions.clear()
                questions?.let { QuestionList.questions.addAll(it) }

                Thread.sleep(1000)

                println(questions)
                mHandler.post {
                    adaptor.notifyDataSetChanged()
                    progress.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }

        })


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

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        adaptor.notifyDataSetChanged()
    }
}