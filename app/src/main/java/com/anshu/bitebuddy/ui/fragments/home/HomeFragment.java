package com.anshu.bitebuddy.ui.fragments.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.databinding.FragmentHomeBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    public HomeFragment() {
        super(R.layout.fragment_home, Transition.Axis.Y);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
    }
}
