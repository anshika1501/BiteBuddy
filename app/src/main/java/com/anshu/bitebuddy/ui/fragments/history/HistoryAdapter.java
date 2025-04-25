package com.anshu.bitebuddy.ui.fragments.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.core.database.model.OrderModel;
import com.anshu.bitebuddy.databinding.ItemHistoryBinding;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Assuming your package structure and imports will match your project

public class HistoryAdapter extends ListAdapter<OrderModel, HistoryAdapter.OrderViewHolder> {

    private static final DiffUtil.ItemCallback<OrderModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderModel oldItem, @NonNull OrderModel newItem) {
            // Assuming OrderModel has some unique identifier field
            // If not, you might need to compare multiple fields
            return oldItem.getOrderedAt().equals(newItem.getOrderedAt());
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrderModel oldItem, @NonNull OrderModel newItem) {
            // Full content comparison
            return oldItem.getFood().getName().equals(newItem.getFood().getName()) &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getOrderedAt().equals(newItem.getOrderedAt()) &&
                    oldItem.getPath().equals(newItem.getPath());
        }
    };

    public HistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(
                ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel currentOrder = getItem(position);
        holder.bind(currentOrder);
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding binding;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        public OrderViewHolder(@NonNull ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderModel order) {
            // Set food name
            binding.textViewFoodName.setText(order.getFood().getName());

            // Format and set order date
            String formattedDate = "Ordered on: " + dateFormat.format(new Date(order.getOrderedAt()));
            binding.textViewOrderDate.setText(formattedDate);

            // Set food rating (assuming Food class has a rating property)
            binding.textViewRating.setText(String.valueOf(order.getFood().getRating()));

            // Set price with currency
            binding.textViewPrice.setText(String.format(Locale.getDefault(), "%.2f Rs", order.getPrice()));

            // Set order status
            binding.textViewOrderStatus.setText(order.getOrderState().toString());

            // Load food image
            Glide.with(binding.getRoot().getContext())
                    .load(order.getFood().getImage())
                    .centerCrop()
                    .error(com.anshu.bitebuddy.R.drawable.food)
                    .into(binding.imageViewFood);
        }
    }
}
