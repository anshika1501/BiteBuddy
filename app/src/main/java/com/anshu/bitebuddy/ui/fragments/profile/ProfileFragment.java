package com.anshu.bitebuddy.ui.fragments.profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;

public class ProfileFragment extends BaseFragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile, Transition.Axis.Y);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
