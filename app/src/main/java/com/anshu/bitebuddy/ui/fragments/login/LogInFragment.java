package com.anshu.bitebuddy.ui.fragments.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.model.User;
import com.anshu.bitebuddy.core.login.GoogleSignInUtils;
import com.anshu.bitebuddy.databinding.FragmentLoginBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LogInFragment extends BaseFragment {
    private static final String TAG = "LogInFragment";

    public LogInFragment() {
        super(R.layout.fragment_login, Transition.Axis.Y);
    }

    private FragmentLoginBinding binding;

    @Inject
    GoogleSignInUtils googleSignInUtils;

    @Inject
    FirebaseAuth firebaseAuth;

    private LogInViewModel logInViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        if (firebaseAuth.getUid() != null) {
            navigateToHome();
        }
        binding.logInGoogle.setOnClickListener(v -> googleSignInUtils.signInGoogle((user, exception) -> {
            if (exception != null) {
                Log.e(TAG, "onViewCreated: " + exception.getMessage());
                return;
            }
            if (user != null) {
                User mUser = new User(user.getDisplayName(), user.getEmail(), user.getPhoneNumber(), "", user.getPhotoUrl().toString(), user.getUid());
                logInViewModel.insertUser(mUser, e -> {
                    if (e != null) {
                        Log.e(TAG, "onViewCreated: " + e.getMessage());
                        return;
                    }
                    navigateToHome();
                });
            }
        }));
    }

    private void navigateToHome() {
        Navigation.findNavController(binding.getRoot()).navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment());
    }
}
