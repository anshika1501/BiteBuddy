package com.anshu.bitebuddy.core.database.interaction;


import com.anshu.bitebuddy.core.database.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.function.Consumer;

import javax.inject.Inject;


public class FirebaseInteraction {

    private static final String TAG = "FirebaseInteraction";

    private static final String USER_DATABASE_PATH = "User";
    private final FirebaseFirestore firebaseFirestore;

    @Inject
    public FirebaseInteraction(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }


    public void insertUser(
            User user,
            Consumer<Exception> onUserAdded
    ) {
        firebaseFirestore.collection(USER_DATABASE_PATH)
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    onUserAdded.accept(null);
                })
                .addOnFailureListener(onUserAdded::accept);
    }

}

// BiteBuddy/user/userId/{data}
