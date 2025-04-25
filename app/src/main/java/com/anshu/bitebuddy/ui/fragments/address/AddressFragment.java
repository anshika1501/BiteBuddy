package com.anshu.bitebuddy.ui.fragments.address;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.interaction.FirebaseInteractionNoDataFoundException;
import com.anshu.bitebuddy.core.database.model.AddressModel;
import com.anshu.bitebuddy.databinding.FragmentAddressBinding;
import com.anshu.bitebuddy.utils.BaseFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddressFragment extends BaseFragment {
    private FragmentAddressBinding binding;
    private AddressFragmentArgs args;

    public AddressFragment() {
        super(R.layout.fragment_address);
    }

    @Inject
    FirebaseInteraction firebaseInteraction;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddressBinding.bind(view);
        args = AddressFragmentArgs.fromBundle(getArguments());
        var adapter = new AddressAdapter();
        binding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewAddress.setAdapter(adapter);
        binding.recyclerViewAddress.setHasFixedSize(true);
        binding.addAddressButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view)
                    .navigate(AddressFragmentDirections.actionAddressFragmentToAddAddressFragment(null));
        });
        firebaseInteraction.getAddress((addressModels, e) -> {
            if (e != null) {
                binding.recyclerViewAddress.setVisibility(View.GONE);
                binding.linearLayoutLoading.setVisibility(View.GONE);
                binding.linearLayoutEmptyAddress.setVisibility(View.VISIBLE);
                if (e instanceof FirebaseInteractionNoDataFoundException) {
                    binding.textViewMessage.setText("No address found");
                    return;
                }
                binding.textViewMessage.setText("Unable to fetch address");
                return;
            }
            binding.recyclerViewAddress.setVisibility(View.VISIBLE);
            binding.linearLayoutLoading.setVisibility(View.GONE);
            binding.linearLayoutEmptyAddress.setVisibility(View.GONE);
            adapter.submitList(addressModels);
            adapter.setAddressClickListener(new AddressAdapter.AddressClickListener() {
                @Override
                public void onAddressClick(AddressModel addressModel) {
                    if (args.getForPlaceOrder() && args.getOrder() != null) {
                        var model = args.getOrder();
                        model.setAddress(addressModel);
//                        Navigation.findNavController(view)
//                                .navigate(
//                                        AddressFragmentDirections.actionAddressFragmentToPlaceOrderFragment(model, args.getFromCart())
//                                );
                    }
                }

                @Override
                public void onAddressEditClick(AddressModel addressModel) {
                    Navigation.findNavController(view)
                            .navigate(AddressFragmentDirections.actionAddressFragmentToAddAddressFragment(addressModel));
                }

                @Override
                public void onAddressDeleteClick(AddressModel addressModel) {
                    firebaseInteraction.removeAddress(addressModel.getPath(), e1 -> {
                        if (e1 != null) {
                            binding.recyclerViewAddress.setVisibility(View.GONE);
                            binding.linearLayoutLoading.setVisibility(View.GONE);
                            binding.linearLayoutEmptyAddress.setVisibility(View.VISIBLE);
                            if (e1 instanceof FirebaseInteractionNoDataFoundException) {
                                binding.textViewMessage.setText("No address found");
                                return;
                            }
                            binding.textViewMessage.setText("Unable to fetch address");
                            return;
                        }
                        binding.recyclerViewAddress.setVisibility(View.VISIBLE);
                        binding.linearLayoutLoading.setVisibility(View.GONE);
                        binding.linearLayoutEmptyAddress.setVisibility(View.GONE);
                    });
                }
            });
        });
    }
}
