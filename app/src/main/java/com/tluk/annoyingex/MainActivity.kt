package com.tluk.annoyingex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.WorkManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.tluk.annoyingex.model.MessageList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue: RequestQueue = Volley.newRequestQueue(this)

        fun getMessageList(onSongsReady: (MessageList) -> Unit, onError: (() -> Unit)? = null) {
            val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"
            val request = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val gson = Gson()
                    val messageList = gson.fromJson(response, MessageList::class.java)
                    onSongsReady(messageList)
                },
                { error ->
                    Log.e("mainactivity", "Error occured: ${error.networkResponse.statusCode}")
                    onError?.invoke()
                }
            )
            queue.add(request)
        }


        val annoyingExApp = (application as AnnoyingExApp)
        val workerManager = annoyingExApp.workerManager

        getMessageList({ messageList ->
            annoyingExApp.messages = messageList.messages
        }, {
            Log.i("info", "error fetching songs")
        })

        btnStart.setOnClickListener {
            workerManager.startAnnoying()
        }
        btnStop.setOnClickListener() {
            workerManager.stopWork()
        }

        val lastMessage = intent.getStringExtra("MessageText")
        tvMessageText.text = lastMessage
    }

}
