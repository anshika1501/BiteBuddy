package com.anshu.bitebuddy.ui.fragments.detail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.databinding.FragmentDetailsBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends BaseFragment {

    private FragmentDetailsBinding binding;
    private DetailViewModel viewModel;
    private DetailsFragmentArgs args;

    public DetailsFragment() {
        super(R.layout.fragment_details, Transition.Axis.Y);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        args = DetailsFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDetailsBinding.bind(view);
        binding.topAppBar.setNavigationOnClickListener(v -> {
            navigateUp();
        });
        var food = args.getFood();
        binding.textViewFoodNameDetails.setText(food.getName());
        binding.textViewDescription.setText(food.getDescription());
        binding.textViewFoodPriceDetails.setText(food.getPrice() + " ₹");
        binding.textViewRatingDetails.setText(String.valueOf(food.getRating()));
        binding.buttonAddToCartDetails.setVisibility(args.getFromCart() ? View.GONE : View.VISIBLE);
        Glide.with(binding.getRoot()).load(food.getImage()).centerCrop().error(R.drawable.baseline_broken_image_24).transition(DrawableTransitionOptions.withCrossFade()).into(binding.imageViewFoodDetails);

    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}
