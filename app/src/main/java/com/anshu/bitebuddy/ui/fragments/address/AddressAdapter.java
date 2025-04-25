package com.anshu.bitebuddy.ui.fragments.address;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.bitebuddy.core.database.model.AddressModel;
import com.anshu.bitebuddy.databinding.ItemAddressBinding;


public class AddressAdapter extends ListAdapter<AddressModel, AddressAdapter.AddressViewHolder> {

    public interface AddressClickListener {
        void onAddressClick(AddressModel addressModel);

        void onAddressEditClick(AddressModel addressModel);

        void onAddressDeleteClick(AddressModel addressModel);
    }

    private AddressClickListener addressClickListener;

    public void setAddressClickListener(AddressClickListener addressClickListener) {
        this.addressClickListener = addressClickListener;
    }

    protected AddressAdapter() {
        super(new DiffUtil.ItemCallback<AddressModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull AddressModel oldItem, @NonNull AddressModel newItem) {
                return oldItem.getPath().equals(newItem.getPath());
            }

            @Override
            public boolean areContentsTheSame(@NonNull AddressModel oldItem, @NonNull AddressModel newItem) {
                return oldItem.getArea().equals(newItem.getArea()) &&
                        oldItem.getHouseName().equals(newItem.getHouseName()) &&
                        oldItem.getLandmark().equals(newItem.getLandmark()) &&
                        oldItem.getName().equals(newItem.getName()) &&
                        oldItem.getPhone().equals(newItem.getPhone()) &&
                        oldItem.getPostalCode() == newItem.getPostalCode() &&
                        oldItem.getState().equals(newItem.getState());
            }
        });
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressViewHolder(
                ItemAddressBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel addressModel = getItem(position);
        holder.bind(addressModel);

    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {

        private ItemAddressBinding binding;

        public AddressViewHolder(
                ItemAddressBinding binding
        ) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AddressModel addressModel) {
            binding.tvAddressName.setText(addressModel.getName());
            binding.tvAddressPhoneNumber.setText(addressModel.getPhone());
            binding.tvAddress.setText(addressModel.getHouseName() + ", " + addressModel.getArea() + ", " + addressModel.getLandmark() + ", " + addressModel.getState() + ", " + addressModel.getPostalCode());
            binding.buttonEditItem.setOnClickListener(v -> {
                if (addressClickListener != null) {
                    addressClickListener.onAddressEditClick(addressModel);
                }
            });
            binding.buttonRemoveItem.setOnClickListener(v -> {
                if (addressClickListener != null) {
                    addressClickListener.onAddressDeleteClick(addressModel);
                }
            });
            binding.getRoot().setOnClickListener(v -> {
                if (addressClickListener != null) {
                    addressClickListener.onAddressClick(addressModel);
                }
            });
        }
    }
}
