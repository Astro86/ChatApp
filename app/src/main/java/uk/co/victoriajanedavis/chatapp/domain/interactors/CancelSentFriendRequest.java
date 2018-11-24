package uk.co.victoriajanedavis.chatapp.domain.interactors;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import uk.co.victoriajanedavis.chatapp.data.repositories.SentFriendRequestRepository;
import uk.co.victoriajanedavis.chatapp.domain.entities.FriendshipEntity;
import uk.co.victoriajanedavis.chatapp.domain.interactors.ReactiveInteractor.DeleteInteractor;

public class CancelSentFriendRequest implements DeleteInteractor<String, FriendshipEntity> {

    @NonNull
    private final SentFriendRequestRepository repository;


    @Inject
    public CancelSentFriendRequest(@NonNull final SentFriendRequestRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public Single<FriendshipEntity> getSingle(@NonNull String username) {
        return repository.cancelSentFriendRequest(username);
    }
}