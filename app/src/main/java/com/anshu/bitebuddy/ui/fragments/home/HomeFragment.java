package com.anshu.bitebuddy.ui.fragments.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.databinding.FragmentHomeBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.CartManager;
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

    private MutableLiveData<FirebaseInteraction.FoodType> foodType = new MutableLiveData<>(FirebaseInteraction.FoodType.ALL);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        var adapter = new FoodItemAdapter();

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.cardViewAll.setOnClickListener(v -> {
            foodType.setValue(FirebaseInteraction.FoodType.ALL);
        });
        binding.cardViewBreakfast.setOnClickListener(v -> {
            foodType.setValue(FirebaseInteraction.FoodType.Breakfast);
        });
        binding.cardViewLunch.setOnClickListener(v -> {
            foodType.setValue(FirebaseInteraction.FoodType.Lunch);
        });
        binding.cardViewDinner.setOnClickListener(v -> {
            foodType.setValue(FirebaseInteraction.FoodType.Dinner);
        });
        binding.cardViewSnack.setOnClickListener(v -> {
            foodType.setValue(FirebaseInteraction.FoodType.Snacks);
        });
        foodType.observe(getViewLifecycleOwner(), foodType -> {
            boolean forceRefresh = false;

            if (forceRefresh) {
                  homeViewModel.invalidateCache(foodType);
            }
            homeViewModel.getFoodData(
                    foodType,
                    foods -> {
                        adapter.submitList(foods);
                        binding.relativeLayoutItems.setVisibility(View.VISIBLE);
                        binding.linearLayoutLoading.setVisibility(View.GONE);
                    },
                    throwable -> {
                        Toast.makeText(requireContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
            );
         });
        adapter.setOnClickListener(food -> {
            Navigation.findNavController(binding.getRoot())
                    .navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(food)
                    );
        });
        adapter.setOnAddToCartClickListener(food -> {
            food.setId(food.getName());
            CartManager.addToCart(
                    food,
                    () -> Toast.makeText(requireContext(), food.getName() + " added to cart", Toast.LENGTH_SHORT).show(),
                    () -> Toast.makeText(requireContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show()
            );
        });

    }
}
