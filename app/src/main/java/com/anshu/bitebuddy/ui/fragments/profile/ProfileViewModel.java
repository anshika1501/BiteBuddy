package com.anshu.bitebuddy.ui.fragments.profile;

import androidx.lifecycle.ViewModel;

import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.model.User;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    private FirebaseInteraction firebaseInteraction;

    @Inject
    public ProfileViewModel(FirebaseInteraction firebaseInteraction) {
        this.firebaseInteraction = firebaseInteraction;
    }

    public void getUser(
            BiConsumer<User, Exception> onUserRetrieved
    ) {
        firebaseInteraction.getUser(onUserRetrieved);
    }

    public void logOut(
            Consumer<Void> onLoggedOut
    ) {
        firebaseInteraction.logOut(onLoggedOut);
    }
}
