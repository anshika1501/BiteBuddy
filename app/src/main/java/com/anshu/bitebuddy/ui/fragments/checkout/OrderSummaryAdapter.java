package com.anshu.bitebuddy.ui.fragments.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.model.Food;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {

    private List<Food> orderList;

    public OrderSummaryAdapter(List<Food> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_summary, parent, false);
        return new OrderSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, int position) {
        Food foodItem = orderList.get(position);
        holder.textName.setText(foodItem.getName());
        holder.textQuantity.setText("Qty: " + foodItem.getQuantity());
        holder.textSubtotal.setText("₹" + (foodItem.getPrice() * foodItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderSummaryViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textQuantity, textSubtotal;

        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            textSubtotal = itemView.findViewById(R.id.textSubtotal);
        }
    }
}
