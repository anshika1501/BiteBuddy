package com.anshu.bitebuddy.ui.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.utils.FoodItemList;

import java.util.List;
import java.util.concurrent.ExecutionException;
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
    FirebaseInteraction firebaseInteraction;

    @Inject
    public HomeViewModel() {

    }

    public LiveData<List<Food>> getFoodData(
            FirebaseInteraction.FoodType foodType
    ) {
        return firebaseInteraction.getFoodData(foodType);
    }


//    public void loadImage() {
//        executorService.execute(() -> {
//            foodItemList.fetchFoodImages();
//        });
//    }

}
