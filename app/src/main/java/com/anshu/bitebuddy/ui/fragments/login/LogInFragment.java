package com.anshu.bitebuddy.ui.fragments.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.databinding.FragmentLoginBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;

public class LogInFragment extends BaseFragment {
    public LogInFragment() {
        super(R.layout.fragment_login, Transition.Axis.Y);
    }

    private FragmentLoginBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        binding.button.setOnClickListener(v -> {
            var action = LogInFragmentDirections.actionLogInFragmentToHomeFragment();
            Navigation
                    .findNavController(binding.getRoot())
                    .navigate(action);

        });
    }
}
