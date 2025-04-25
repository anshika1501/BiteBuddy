package com.anshu.bitebuddy.utils;

import android.util.Log;

import com.anshu.bitebuddy.core.database.model.CartItem;
import com.anshu.bitebuddy.core.database.model.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems = new ArrayList<>();

    private CartManager() {}
    public static void addToCart(Food food, Runnable onSuccess, Runnable onFailure) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Use name as fallback if ID is null
        String foodId = food.getId() != null ? food.getId() : food.getName();
        food.setId(foodId); // Make sure ID is set in the food object

        firestore.collection("User")
                .document(uid)
                .collection("cart")
                .document(foodId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Already in cart — update quantity
                        Food existingFood = documentSnapshot.toObject(Food.class);
                        if (existingFood != null) {
                            int newQuantity = existingFood.getQuantity() + 1;
                            firestore.collection("users")
                                    .document(uid)
                                    .collection("cart")
                                    .document(foodId)
                                    .update("quantity", newQuantity)
                                    .addOnSuccessListener(unused -> onSuccess.run())
                                    .addOnFailureListener(e -> onFailure.run());
                        } else {
                            onFailure.run();
                        }
                    } else {
                        // New item — add with quantity 1
                        food.setQuantity(1);
                        firestore.collection("User")
                                .document(uid)
                                .collection("cart")
                                .document(foodId)
                                .set(food)
                                .addOnSuccessListener(unused -> onSuccess.run())
                                .addOnFailureListener(e -> onFailure.run());
                    }
                })
                .addOnFailureListener(e -> onFailure.run());
        Log.d("CartDebug", "Added item: " + food.getName());

    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems); // Return copy
    }

    public void clearCart() {
        cartItems.clear();
    }
}

