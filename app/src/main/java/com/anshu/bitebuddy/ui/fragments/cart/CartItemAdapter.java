package com.anshu.bitebuddy.ui.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.model.Food;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder> {

    private List<Food> cartItems;
    private OnCartItemInteractionListener listener;

    public interface OnCartItemInteractionListener {
        void onIncreaseQuantity(Food item);
        void onDecreaseQuantity(Food item);
        void onRemoveItem(Food item);
    }

    public CartItemAdapter(List<Food> cartItems, OnCartItemInteractionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void updateCartItems(List<Food> items) {
        this.cartItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Food item = cartItems.get(position);

        holder.foodName.setText(item.getName());
        holder.price.setText(item.getPrice() + " Rs");
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .placeholder(R.drawable.food)
                .into(holder.foodImage);

      //  holder.increaseQuantity.setOnClickListener(v -> listener.onIncreaseQuantity(item));
     //   holder.decreaseQuantity.setOnClickListener(v -> listener.onDecreaseQuantity(item));
     //   holder.removeButton.setOnClickListener(v -> listener.onRemoveItem(item));
    }

    @Override
    public int getItemCount() {
        return cartItems == null ? 0 : cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, price, quantity;
        
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.image_view_food);
            foodName = itemView.findViewById(R.id.text_view_food_name);
            price = itemView.findViewById(R.id.text_view_price);
            quantity = itemView.findViewById(R.id.text_view_quantity);
          //  increaseQuantity = itemView.findViewById(R.id.button_increase_quantity);
          //  decreaseQuantity = itemView.findViewById(R.id.button_decrease_quantity);
           // removeButton = itemView.findViewById(R.id.button_remove_from_cart);
        }
    }
}
