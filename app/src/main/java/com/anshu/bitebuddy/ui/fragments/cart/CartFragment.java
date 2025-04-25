package com.anshu.bitebuddy.ui.fragments.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.utils.BaseFragment;
import com.anshu.bitebuddy.utils.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment implements CartItemAdapter.OnCartItemInteractionListener {
    private TextView textTotalAmount;

    private RecyclerView recyclerView;
    private CartItemAdapter adapter;
    private final List<Food> cartItems = new ArrayList<>();
    private FirebaseFirestore firestore;
    private DocumentReference userCartRef;
    private ListenerRegistration cartListener;
    private static final String userCartPath = "User";

    public CartFragment() {
        super(R.layout.fragment_cart, Transition.Axis.Y);
    }

    private double calculateTotal() {
        double total = 0.0;
        for (Food item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CartItemAdapter(cartItems, this);
        recyclerView.setAdapter(adapter);

        textTotalAmount = view.findViewById(R.id.textTotalAmount);

        firestore = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userCartRef = firestore.collection(userCartPath).document(uid).collection("cart").document(); // adjust if needed

        listenToCartUpdates(uid);
    }

    private void listenToCartUpdates(String uid) {
        cartListener = firestore.collection(userCartPath).document(uid).collection("cart")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        Toast.makeText(requireContext(), "Error loading cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cartItems.clear();
                    for (var doc : value.getDocuments()) {
                        Food item = doc.toObject(Food.class);
                        if (item != null) {
                            item.setId(doc.getId());
                            cartItems.add(item);
                        }
                    }
                    adapter.updateCartItems(cartItems);
                    textTotalAmount.setText("Total: ₹" + calculateTotal());

                });
        Log.d("CartDebug", "Loaded items: " + cartItems.size());

    }

    @Override
    public void onIncreaseQuantity(Food item) {
        updateQuantity(item, item.getQuantity() + 1);
    }

    @Override
    public void onDecreaseQuantity(Food item) {
        int newQuantity = item.getQuantity() - 1;
        if (newQuantity <= 0) {
            onRemoveItem(item);
        } else {
            updateQuantity(item, newQuantity);
        }
    }

    private void updateQuantity(Food item, int newQuantity) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection(userCartPath).document(uid).collection("cart")
                .document(item.getId())
                .update("quantity", newQuantity);
    }

    @Override
    public void onRemoveItem(Food item) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection(userCartPath).document(uid).collection("cart")
                .document(item.getId()).delete();
        Toast.makeText(requireContext(), "Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Food item) {
        Navigation.findNavController(recyclerView)
                .navigate(CartFragmentDirections.actionCartFragmentToDetailsFragment(item, true));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cartListener != null) cartListener.remove();
    }
}
