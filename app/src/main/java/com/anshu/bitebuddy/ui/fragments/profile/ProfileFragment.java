package com.anshu.bitebuddy.ui.fragments.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.databinding.FragmentProfileBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;
import com.bumptech.glide.Glide;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends BaseFragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile, Transition.Axis.Y);
    }


    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);
        profileViewModel.getUser((user, exception) -> {
            if (exception != null) {
                Toast.makeText(requireContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (user != null) {
                Glide.with(requireContext()).load(user.getProfilePic()).into(binding.imageViewProfile);
                binding.textViewUserName.setText(user.getName());
                binding.textViewUserEmail.setText(user.getEmail());
                binding.textViewPhoneNumber.setText((user.getPhone() == null || user.getPhone().isBlank()) ? "Not Provided" : user.getPhone());
                binding.textViewAddress.setText((user.getAddress() == null || user.getAddress().isBlank()) ? "Not Provided" : user.getAddress());
            }
        });

        binding.buttonLogout.setOnClickListener(v -> {
            profileViewModel.logOut(unused -> {
                Navigation.findNavController(binding.getRoot()).navigate(ProfileFragmentDirections.actionProfileFragmentToLogInFragment());
            });
        });
    }
}
