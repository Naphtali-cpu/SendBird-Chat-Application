package com.example.testapplication.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.creatingchannel.ChannelCreateAdapter
import com.example.testapplication.creatingchannel.ChannelCreateAdapter.Companion.selectedUsers
import com.example.testapplication.creatingchannel.ChannelCreateAdapter.Companion.sparseArray
import com.sendbird.android.User
import kotlinx.android.synthetic.main.item_create.view.*

class UserHolder(view: View) : RecyclerView.ViewHolder(view) {

    val checkbox = view.checkbox_create
    val userId = view.text_create_user

    fun bindViews(user: User, position: Int, listener: ChannelCreateAdapter.OnItemCheckedChangeListener) {

        userId.text = user.userId

        checkbox.isChecked = sparseArray().get(position, false)

        checkbox.setOnCheckedChangeListener() {buttonView, isChecked ->
            listener.onItemChecked(user, isChecked)

            if (isChecked) {
                selectedUsers().add(user.userId)
                sparseArray().put(position, true)
            } else {
                selectedUsers().remove(user.userId)
                sparseArray().put(position, false)
            }
        }

    }


}