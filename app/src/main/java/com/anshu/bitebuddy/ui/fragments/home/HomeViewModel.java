package com.anshu.bitebuddy.ui.fragments.home;

import androidx.lifecycle.ViewModel;

import com.anshu.bitebuddy.utils.FoodItemList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Inject
    FoodItemList foodItemList;

    @Inject
    public HomeViewModel() {

    }

    public void loadImage() {
        executorService.execute(() -> {
            foodItemList.fetchFoodImages();
        });
    }

}
