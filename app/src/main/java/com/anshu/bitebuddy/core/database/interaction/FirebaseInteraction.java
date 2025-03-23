package com.anshu.bitebuddy.core.database.interaction;


import com.anshu.bitebuddy.core.database.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;


public class FirebaseInteraction {

    private static final String TAG = "FirebaseInteraction";

    private static final String USER_DATABASE_PATH = "User";
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseInteraction(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }


    public void insertUser(User user, Consumer<Exception> onUserAdded) {
        firebaseFirestore.collection(USER_DATABASE_PATH).document(user.getUid()).set(user).addOnSuccessListener(aVoid -> {
            onUserAdded.accept(null);
        }).addOnFailureListener(onUserAdded::accept);
    }

    public void getUser(BiConsumer<User, Exception> onUserRetrieved) {
        if (firebaseAuth.getUid() == null) {
            onUserRetrieved.accept(null, new Exception("User not logged in"));
            return;
        }
        firebaseFirestore.collection(USER_DATABASE_PATH).document(firebaseAuth.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            onUserRetrieved.accept(user, null);
        }).addOnFailureListener(e -> {
            onUserRetrieved.accept(null, e);
        });
    }

    public void logOut(
            Consumer<Void> onLoggedOut
    ) {
        firebaseAuth.signOut();
        onLoggedOut.accept(null);
    }

}

// BiteBuddy/user/userId/{data}
