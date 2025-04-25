package com.anshu.bitebuddy.ui.fragments.address;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.model.AddressModel;
import com.anshu.bitebuddy.databinding.FragmentAddAddressBinding;
import com.anshu.bitebuddy.utils.BaseFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddAddressFragment extends BaseFragment {
    private FragmentAddAddressBinding binding;
    private AddAddressFragmentArgs args;

    @Inject
    FirebaseInteraction firebaseInteraction;

    private AddressModel addressModel;

    public AddAddressFragment() {
        super(R.layout.fragment_add_address);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddAddressBinding.bind(view);
        args = AddAddressFragmentArgs.fromBundle(getArguments());
        addressModel = args.getAddress();
        if (addressModel != null) {
            binding.textFieldAddressName.getEditText().setText(addressModel.getName());
            binding.textFieldAddressPhoneNumber.getEditText().setText(addressModel.getPhone());
            binding.textFieldAddressHouseNumber.getEditText().setText(addressModel.getHouseName());
            binding.textFieldArea.getEditText().setText(addressModel.getArea());
            binding.textFieldAddressLandmark.getEditText().setText(addressModel.getLandmark());
            binding.textFieldAddressPin.getEditText().setText(String.valueOf(addressModel.getPostalCode()));
            binding.textFieldState.getEditText().setText(addressModel.getState());
        }
        binding.buttonSaveAddress.setOnClickListener(v -> {
            String name = binding.textFieldAddressName.getEditText().getText().toString();
            String phone = binding.textFieldAddressPhoneNumber.getEditText().getText().toString();
            String houseName = binding.textFieldAddressHouseNumber.getEditText().getText().toString();
            String area = binding.textFieldArea.getEditText().getText().toString();
            String landmark = binding.textFieldAddressLandmark.getEditText().getText().toString();
            int postalCode = Integer.parseInt(binding.textFieldAddressPin.getEditText().getText().toString());
            String state = binding.textFieldState.getEditText().getText().toString();

//            validate inputs as only landmark is optional
            if (name.isEmpty()) {
                binding.textFieldAddressName.setError("Name is required");
                return;
            }
            if (phone.isEmpty()) {
                binding.textFieldAddressPhoneNumber.setError("Phone number is required");
                return;
            }
            if (houseName.isEmpty()) {
                binding.textFieldAddressHouseNumber.setError("House no.,Building name is required");
                return;
            }
            if (area.isEmpty()) {
                binding.textFieldArea.setError("Area/Street is required");
                return;
            }
            if (postalCode == 0) {
                binding.textFieldAddressPin.setError("Postal code is required");
                return;
            }
            if (state.isEmpty()) {
                binding.textFieldState.setError("State is required");
                return;
            }

            AddressModel addressModel = new AddressModel(name, phone, houseName, area, landmark, postalCode, state);
            if (args != null && args.getAddress() != null)
                addressModel.setPath(args.getAddress().getPath());
            else addressModel.setPath();
            firebaseInteraction.addAddress(addressModel, e -> {
                if (e != null) {
                    Toast.makeText(requireContext(), "Error in Saving Address!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Address Saved Successfully!!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigateUp();
                }
            });
        });
    }
}
