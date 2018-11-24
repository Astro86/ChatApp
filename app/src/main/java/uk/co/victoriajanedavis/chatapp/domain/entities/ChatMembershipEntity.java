package uk.co.victoriajanedavis.chatapp.domain.entities;

import java.util.List;
import java.util.UUID;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class ChatMembershipEntity {

    @NonNull private UUID uuid;
    @Nullable private FriendshipEntity friendship;
    @Nullable private List<MessageEntity> messages;


    public ChatMembershipEntity() {}

    public ChatMembershipEntity(UUID uuid) {
        this.uuid = uuid;
    }

    @NonNull
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    @Nullable
    public FriendshipEntity getFriendship() {
        return friendship;
    }

    public void setFriendship(FriendshipEntity friendship) {
        this.friendship = friendship;
    }

    @Nullable
    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof ChatMembershipEntity))return false;
        return this.getUuid().equals(((ChatMembershipEntity) other).getUuid());
    }

}