package com.anshu.bitebuddy.utils;

import static com.anshu.bitebuddy.utils.Transition.applyEnterTransition;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private Transition.Axis axis;

    public BaseFragment(int layoutId, Transition.Axis axis) {
        super(layoutId);
        this.axis = axis;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyEnterTransition(
                this,
                axis
        );
    }
}
