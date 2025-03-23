package com.anshu.bitebuddy.ui.fragments.login;

import androidx.lifecycle.ViewModel;

import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.model.User;

import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LogInViewModel extends ViewModel {
    private final FirebaseInteraction firebaseInteraction;

    @Inject
    public LogInViewModel(FirebaseInteraction firebaseInteraction) {
        this.firebaseInteraction = firebaseInteraction;
    }

    public void insertUser(
            User user,
            Consumer<Exception> onUserAdded
    ) {
        firebaseInteraction.insertUser(
                user,
                onUserAdded
        );
    }
}
