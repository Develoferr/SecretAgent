package com.tutorial.fakebot.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutorial.fakebot.viewmodel.MainViewModel
import com.tutorial.fakebot.R
import com.tutorial.fakebot.databinding.ActivityMainBinding
import com.tutorial.fakebot.model.FbMessage

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FbAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.fbRecycler.layoutManager = LinearLayoutManager(this)
        adapter = FbAdapter(this)
        binding.fbRecycler.adapter = adapter

        viewModel.fbMessageListLiveData.observe(this, Observer {
                chatMessageList ->
            adapter.submitList(chatMessageList)
            binding.fbRecycler.scrollToPosition(chatMessageList.size - 1)
            binding.chatView.visibility = if (chatMessageList.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        setupSendMessageLayout(binding)
    }

    private fun setupSendMessageLayout(binding: ActivityMainBinding) {
        binding.sendMessageButton.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(this, getString(R.string.message_must_not_be_empty),
                    Toast.LENGTH_SHORT).show()
            } else {
                val chatMessage = FbMessage(System.currentTimeMillis(), message, true)
                viewModel.addMessage(chatMessage)
                viewModel.createResponse()
                binding.messageEdit.setText("")
            }
        }
    }

}