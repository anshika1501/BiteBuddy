package com.anshu.bitebuddy.ui.fragments.placeorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.model.AddressModel;
import com.anshu.bitebuddy.core.database.model.OrderModel;
import com.anshu.bitebuddy.databinding.FragmentPlaceOrderBinding;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlaceOrderFragment extends BaseFragment {

    public PlaceOrderFragment() {
        super(R.layout.fragment_place_order);
    }

    private FragmentPlaceOrderBinding binding;
    @Inject
    FirebaseInteraction firebaseInteraction;
    private PlaceOrderFragmentArgs args;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentPlaceOrderBinding.bind(view);
        args = PlaceOrderFragmentArgs.fromBundle(getArguments());
        var order = args.getOrder();

        // Initialize Firebase interaction

        // Set up food details
        binding.foodName.setText(order.getFood().getName());
        binding.foodDescription.setText(order.getFood().getDescription());
        binding.foodPrice.setText(order.getFood().getPrice() + "");
        binding.totalPrice.setText("Total: ₹ " + order.getFood().getPrice());

        // Load food image
        Glide.with(requireContext())
                .load(order.getFood().getImage())
                .error(R.drawable.food)
                .into(binding.foodImage);

        // Set up quantity controls
        binding.increaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(binding.quantityText.getText().toString());
            quantity++;
            binding.quantityText.setText(String.valueOf(quantity));
            binding.totalPrice.setText("Total: ₹ " + order.getFood().getPrice());
            order.getFood().setQuantity(quantity);
            order.setPrice(order.getFood().getPrice() * quantity);
        });

        binding.decreaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(binding.quantityText.getText().toString());
            if (quantity > 1) {
                quantity--;
                binding.quantityText.setText(String.valueOf(quantity));
                binding.totalPrice.setText("Total: ₹ " + order.getFood().getPrice());
                order.getFood().setQuantity(quantity);
                order.setPrice(order.getFood().getPrice() * quantity);
            }
        });

        // Pre-fill address fields if address exists
        if (order.getAddress() != null) {
            populateAddressFields(order.getAddress());
        }

        // Set up place order button
        binding.placeOrderButton.setOnClickListener(v -> {
            saveOrder(order);
        });
    }

    private void populateAddressFields(AddressModel address) {
        binding.addressLine1.setText(address.getHouseName());
        binding.addressLine2.setText(address.getLandmark());
        binding.city.setText(address.getArea());
        binding.state.setText(address.getState());
        binding.zipCode.setText(String.valueOf(address.getPostalCode()));
    }



    private void saveOrder(OrderModel order) {
        // Show loading state
        binding.placeOrderButton.setEnabled(false);
        binding.placeOrderButton.setText("Processing...");

        firebaseInteraction.addOrder(order, e -> {
            if (e == null) {
                // Order placed successfully
                showToast("Order placed successfully");
                Navigation.findNavController(binding.getRoot())
                        .navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToHistoryFragment());
            } else {
                // Handle error
                showToast("Failed to place order: " + e.getMessage());
            }
        });
    }

    private void showToast(String s) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
