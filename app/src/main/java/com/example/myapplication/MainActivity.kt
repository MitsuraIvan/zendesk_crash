package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.content.Context
import zendesk.chat.Chat
import zendesk.chat.ChatConfiguration
import zendesk.chat.ChatEngine
import zendesk.chat.ChatProvidersConfiguration
import zendesk.chat.VisitorInfo
import zendesk.classic.messaging.MessagingActivity

abstract class ZendeskChatActivity {
    companion object {

        private var isZendeskInited = false

        fun startActivity(
            context: Context,
            zendeskKey: String,
            zendeskId: String,
            accountId: String,
        ) {
            val chat = Chat.INSTANCE

            if (!isZendeskInited) {
                isZendeskInited = true
                chat.init(context, zendeskKey, zendeskId)
            }
            if (chat.hasIdentity()) {
                chat.resetIdentity()
            }
            val visitorInfo = VisitorInfo.builder()
                .withName(accountId)
                .withEmail("$accountId@gmail.com")
                .withPhoneNumber(accountId)
                .build()

            val chatProvidersConfiguration = ChatProvidersConfiguration.builder()
                .withVisitorInfo(visitorInfo)
                .withDepartment("ENGLISH")
                .build()

            chat.chatProvidersConfiguration = chatProvidersConfiguration

            val chatConfiguration = ChatConfiguration.builder()
                .withAgentAvailabilityEnabled(false)
                .build()

            MessagingActivity.builder()
                .withEngines(ChatEngine.engine())
                .show(context, chatConfiguration)
        }
    }
}

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ZendeskChatActivity.startActivity(
            this, "498105336425943041",
            "45wy36xV37MFFAu9msiZXFFxpynEpGeH",
            "500105397",
        )
        //zendesk init
    }
}