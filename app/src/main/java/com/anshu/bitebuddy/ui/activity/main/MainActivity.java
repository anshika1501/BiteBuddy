package com.anshu.bitebuddy.ui.activity.main;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.anshu.bitebuddy.R;
import com.anshu.bitebuddy.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;

        });

        var navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentContainerView.getId());
        if (navHostFragment != null) navController = navHostFragment.getNavController();
        else throw new IllegalStateException("NavHostFragment not found");
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnItemReselectedListener(item -> {

        });
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.homeFragment || navDestination.getId() == R.id.cartFragment || navDestination.getId() == R.id.profileFragment || navDestination.getId() == R.id.historyFragment) {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                binding.bottomNavigationView.setVisibility(View.GONE);
            }
        });

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.cartFragment, R.id.profileFragment, R.id.logInFragment, R.id.historyFragment).build();
        NavigationUI.setupWithNavController(binding.topAppBar, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}