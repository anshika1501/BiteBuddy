package com.anshu.bitebuddy.core.login;

import android.content.Context;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CredentialOption;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.anshu.bitebuddy.BuildConfig;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.function.BiConsumer;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;


public class GoogleSignInUtils {

    private final Context context;
    private final CredentialManager credentialManager;
    private GetCredentialRequest getCredRequest;
    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    public GoogleSignInUtils(@ApplicationContext Context context) {
        this.context = context;
        this.credentialManager = CredentialManager.create(context);
        this.getCredRequest = new GetCredentialRequest.Builder()
                .addCredentialOption(getCredentialOption())
                .build();
    }


    public void signInGoogle(
            BiConsumer<FirebaseUser, Exception> signInDone
    ) {
        credentialManager.getCredentialAsync(
                context,
                getCredRequest,
                new CancellationSignal(),
                context.getMainExecutor(),
                new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(GetCredentialResponse getCredentialResponse) {
                        if (getCredentialResponse.getCredential() instanceof CustomCredential) {
                            if (getCredentialResponse.getCredential().getType()
                                    .equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                                GoogleIdTokenCredential googleIdTokenCredential =
                                        GoogleIdTokenCredential.createFrom(
                                                getCredentialResponse.getCredential().getData()
                                        );
                                String googleTokenId = googleIdTokenCredential.getIdToken();
                                AuthCredential authCredential = GoogleAuthProvider
                                        .getCredential(
                                                googleTokenId,
                                                null
                                        );
                                var user =
                                        firebaseAuth
                                                .signInWithCredential(
                                                        authCredential
                                                ).addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        signInDone.accept(task.getResult().getUser(), null);
                                                    } else {
                                                        signInDone.accept(null, task.getException());
                                                    }
                                                })
                                                .addOnFailureListener(e -> {
                                                    signInDone.accept(null, e);
                                                });

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        signInDone.accept(null, e);
                    }
                }
        );
    }


    private CredentialOption getCredentialOption() {
        return new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(BuildConfig.firebaseWebClient)
                .build();
    }


}
