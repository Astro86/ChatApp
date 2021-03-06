package uk.co.victoriajanedavis.chatapp.presentation.ui.friendrequests.sent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_sent_friend_request.*
import uk.co.victoriajanedavis.chatapp.R
import uk.co.victoriajanedavis.chatapp.domain.entities.FriendshipEntity
import uk.co.victoriajanedavis.chatapp.domain.entities.FriendshipLoadingState.*
import uk.co.victoriajanedavis.chatapp.presentation.common.BaseViewHolder
import uk.co.victoriajanedavis.chatapp.presentation.common.ext.*
import java.util.UUID

class SentFriendRequestViewHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    private val clickListener: OnClickListener
) : BaseViewHolder<FriendshipEntity>(
    layoutInflater.inflate(R.layout.item_sent_friend_request, parent, false)
) {

    private lateinit var friendshipEntity: FriendshipEntity

    init {
        cancelButton.setOnClickListener {
            stateCanceling()
            clickListener.onCancelClicked(receiverUserUuid = friendshipEntity.uuid!!)
        }
    }

    override fun bind(item: FriendshipEntity) {
        friendshipEntity = item
        iconTextView.text = item.username[0].toString()
        nameTextView.text = item.username

        val usernameText = "Username: ${item.username}"
        usernameTextView.text = usernameText
        val emailText = "Email: ${item.email}"
        emailTextView.text = emailText

        when(friendshipEntity.loadingState) {
            CANCEL -> stateCanceling()
            else -> stateNormal()
        }
    }

    private fun stateCanceling() {
        cancelButtonProgressBar.visible()
        cancelButton.disable()
    }

    private fun stateNormal() {
        cancelButtonProgressBar.gone()
        cancelButton.enable()
    }

    interface OnClickListener {
        fun onCancelClicked(receiverUserUuid: UUID)
    }
}