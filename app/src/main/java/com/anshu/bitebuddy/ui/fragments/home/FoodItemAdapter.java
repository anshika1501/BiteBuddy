package com.anshu.bitebuddy.ui.fragments.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.databinding.ItemFoodBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.Objects;

public class FoodItemAdapter extends ListAdapter<Food, FoodItemAdapter.FoodViewHolder> {

    public FoodItemAdapter() {
        super(new DiffUtil.ItemCallback<Food>() {
            @Override
            public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
                return Objects.equals(oldItem.getPath(), newItem.getPath());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        ItemFoodBinding binding = ItemFoodBinding.bind(holder.itemView);
        Food food = getItem(position);
        binding.textViewFoodName.setText(food.getName());
        binding.textViewPrice.setText(String.valueOf(food.getPrice()));
        binding.textViewRating.setText(String.valueOf(food.getRating()));
        Glide.with(binding.getRoot())
                .load(food.getImage()).centerCrop()
                .error(R.drawable.baseline_broken_image_24)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageViewFood);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        public FoodViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
        }
    }
}
