package com.tutorial.fakebot.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tutorial.fakebot.model.FbMessage
import java.util.*

class MainViewModel: ViewModel() {
    private var _chatMessageListLiveData = MutableLiveData<MutableList<FbMessage>>()
    val fbMessageListLiveData: LiveData<MutableList<FbMessage>>
        get() = _chatMessageListLiveData

    private val responses = arrayOf("Yes", "Question again", "No", "It's very probable", "I don't think so",
        "May be", "I don't know")

    private var handler: Handler = Handler()

    init {
        _chatMessageListLiveData.value = mutableListOf()
    }

    fun addMessage(fbMessage: FbMessage) {
        val mutableList = _chatMessageListLiveData.value!!
        mutableList.add(fbMessage)
        _chatMessageListLiveData.value = mutableList
    }

    fun createResponse() {
        val runnable = Runnable {
            val random = Random().nextInt(responses.size)

            val response = responses[random]
            val chatMessage = FbMessage(System.currentTimeMillis(), response, false)
            val mutableList = _chatMessageListLiveData.value!!
            mutableList.add(chatMessage)
            _chatMessageListLiveData.value = mutableList
        }

        handler.postDelayed(runnable, 2000)
    }
}