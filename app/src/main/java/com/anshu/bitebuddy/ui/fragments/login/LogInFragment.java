package com.anshu.bitebuddy.ui.fragments.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.login.GoogleSignInUtils;
import com.anshu.bitebuddy.databinding.FragmentLoginBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        binding.logInGoogle.setOnClickListener(v -> googleSignInUtils.signInGoogle((user, exception) -> {
            if (exception != null) {
                Log.e(TAG, "onViewCreated: " + exception.getMessage());
                return;
            }
            if (user != null) {
                Log.d("AAA", "onViewCreated: " + user.getEmail() + " " + user.getDisplayName());
            }
        }));
    }
}
