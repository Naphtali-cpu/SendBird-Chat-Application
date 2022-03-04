package com.example.testapplication
import android.content.Intent
import com.sendbird.android.SendBird
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SendBird.init("7397A4D7-5E74-42AF-8A70-31D11D9BD66A", this)

        button_login_connect.setOnClickListener {
            connectToSendBird(edittext_login_user_id.text.toString(), edittext_login_nickname.text.toString())
        }
    }

    private fun connectToSendBird(userID: String, nickname: String) {
        SendBird.connect(userID) { user, e ->
            if (e != null) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo(nickname, null) { e ->
                    if (e != null) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                    val intent = Intent(this,  ChannelListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}