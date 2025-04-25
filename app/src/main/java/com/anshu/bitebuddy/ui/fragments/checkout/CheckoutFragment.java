package com.anshu.bitebuddy.ui.fragments.checkout;
import com.anshu.bitebuddy.ui.fragments.checkout.OrderSummaryAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.ui.fragments.checkout.OrderSummaryAdapter;

import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CheckoutFragment extends BaseFragment {

    private RecyclerView recyclerViewOrderSummary;
    private TextView textTotalAmount;
    private MaterialButton buttonConfirmOrder;

    private ArrayList<Food> cartItems;
    private OrderSummaryAdapter adapter;

    public CheckoutFragment() {
        super(R.layout.fragment_checkout, Transition.Axis.X);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewOrderSummary = view.findViewById(R.id.recyclerViewOrderSummary);
        textTotalAmount = view.findViewById(R.id.textTotalAmount);
        buttonConfirmOrder = view.findViewById(R.id.buttonConfirmOrder);

        cartItems = (ArrayList<Food>) getArguments().getSerializable("cartItems");
        if (cartItems == null) cartItems = new ArrayList<>();

        adapter = new OrderSummaryAdapter(cartItems);
        recyclerViewOrderSummary.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewOrderSummary.setAdapter(adapter);

        double totalAmount = calculateTotal();
        textTotalAmount.setText("Total: ₹" + totalAmount);

        buttonConfirmOrder.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show();
                  });
    }

    private double calculateTotal() {
        double total = 0.0;
        for (Food item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
