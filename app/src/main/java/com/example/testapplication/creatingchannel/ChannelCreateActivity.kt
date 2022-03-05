package com.example.testapplication.creatingchannel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.ChannelActivity
import com.example.testapplication.R
import com.sendbird.android.GroupChannel
import com.sendbird.android.GroupChannelParams
import com.sendbird.android.SendBird
import com.sendbird.android.User
import kotlinx.android.synthetic.main.activity_channel_create.*

class ChannelCreateActivity : AppCompatActivity(),
    ChannelCreateAdapter.OnItemCheckedChangeListener {
    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"


    lateinit var selectedUsers: ArrayList<String>
    lateinit var adapter: ChannelCreateAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_create)

        selectedUsers = ArrayList()

        adapter = ChannelCreateAdapter(this)
        recyclerView = recycler_create
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadUsers()
        button_create.setOnClickListener {
            createChannel(selectedUsers)
        }
    }

    private fun createChannel(users: MutableList<String>) {
        val params = GroupChannelParams()

        val operatorId = ArrayList<String>()
        operatorId.add(SendBird.getCurrentUser().userId)

        params.addUserIds(users)
        params.setOperatorUserIds(operatorId)

        GroupChannel.createChannel(params) { groupChannel, e ->
            if (e != null) {
                Log.e("TAG", e.message.toString())
            } else {
                val intent = Intent(this, ChannelActivity::class.java)
                intent.putExtra(EXTRA_CHANNEL_URL, groupChannel.url)
                startActivity(intent)
            }
        }
    }

    private fun loadUsers() {
        val userListQuery = SendBird.createApplicationUserListQuery()

        userListQuery.next() { list, e ->
            if (e != null) {
                Log.e("TAG", e.message.toString())
            } else {
                adapter.addUsers(list)
            }
        }
    }



    override fun onItemChecked(user: User, checked: Boolean) {
        if (checked) {
            selectedUsers.add(user.userId)
        } else {
            selectedUsers.remove(user.userId)
        }
    }
}