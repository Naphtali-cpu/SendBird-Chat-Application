package com.example.testapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.adapters.ChannelListAdapter
import com.example.testapplication.creatingchannel.ChannelCreateActivity
import com.sendbird.android.GroupChannel
import kotlinx.android.synthetic.main.activity_channel_list.*

class ChannelListActivity : AppCompatActivity(), ChannelListAdapter.OnChannelClickedListener {

    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"

    lateinit var recyclerView: RecyclerView

    lateinit var adapter: ChannelListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_list)

        adapter = ChannelListAdapter(this)
        recyclerView = recycler_group_channels
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab_group_channel_create.setOnClickListener {
            val intent = Intent(this, ChannelCreateActivity::class.java)
            startActivity(intent)
        }

        addChannels()
    }

    private fun addChannels() {
        val channelList = GroupChannel.createMyGroupChannelListQuery()
        channelList.limit = 100
        channelList.next { list, e ->
            if (e != null) {
                Log.e("TAG", e.message.toString())
            }
            adapter.addChannels(list)
        }
    }

    override fun onItemClicked(channel: GroupChannel) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra(EXTRA_CHANNEL_URL, channel.url)
        startActivity(intent)
    }
}