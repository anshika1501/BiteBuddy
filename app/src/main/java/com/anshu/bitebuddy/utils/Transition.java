package com.anshu.bitebuddy.utils;

import androidx.fragment.app.Fragment;

import com.google.android.material.transition.MaterialSharedAxis;

public class Transition {

    public enum Axis {
        X(MaterialSharedAxis.X),
        Y(MaterialSharedAxis.Y),
        Z(MaterialSharedAxis.Z);

        final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static void applyExitTransition(Fragment fragment) {
        applyExitTransition(fragment, Axis.Y);
    }

    public static void applyExitTransition(Fragment fragment, Axis axis) {
        fragment.setExitTransition(new MaterialSharedAxis(axis.value, true));
        fragment.setReenterTransition(new MaterialSharedAxis(axis.value, false));
    }

    public static void applyEnterTransition(Fragment fragment) {
        applyEnterTransition(fragment, Axis.Y);
    }

    public static void applyEnterTransition(Fragment fragment, Axis axis) {
        fragment.setEnterTransition(new MaterialSharedAxis(axis.value, true));
        fragment.setReturnTransition(new MaterialSharedAxis(axis.value, false));
    }
}