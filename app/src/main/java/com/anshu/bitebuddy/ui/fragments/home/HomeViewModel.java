package com.anshu.bitebuddy.ui.fragments.home;

import androidx.lifecycle.ViewModel;

import com.anshu.bitebuddy.core.database.interaction.FirebaseInteraction;
import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.utils.FoodItemList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Inject
    FoodItemList foodItemList;
    @Inject
    FirebaseInteraction firebaseInteraction;
    private final Map<FirebaseInteraction.FoodType, List<Food>> foodCache = new HashMap<>();
    private final Map<FirebaseInteraction.FoodType, Long> cacheTimetamps = new HashMap<>();
    private static final long CACHE_VALIDITY_DURATION = 5 * 60 * 1000; // 5 minutes

    @Inject
    public HomeViewModel() {
           }
    public void getFoodData(
            FirebaseInteraction.FoodType foodType,
            Consumer<List<Food>> onSuccess,
            Consumer<Throwable> onError
    ) {
        // Check if cached data exists and is valid
        if (isCacheValid(foodType)) {
            onSuccess.accept(foodCache.get(foodType));
            return;
        }
        // Fetch data from Firebase
        firebaseInteraction.getFoodData(
                foodType,
                foods -> {
                    // Cache the fetched data
                    foodCache.put(foodType, foods);
                    cacheTimetamps.put(foodType, System.currentTimeMillis());

                    // Notify success
                    onSuccess.accept(foods);
                },
                onError
        );
    }

    private boolean isCacheValid(FirebaseInteraction.FoodType foodType) {
        // Check if cache exists and is not expired
        return foodCache.containsKey(foodType) &&
                (System.currentTimeMillis() - cacheTimetamps.getOrDefault(foodType, 0L)) < CACHE_VALIDITY_DURATION;
    }

    public void invalidateCache(FirebaseInteraction.FoodType foodType) {
        // Manually invalidate cache for a specific food type
        foodCache.remove(foodType);
        cacheTimetamps.remove(foodType);
    }

    public void invalidateAllCache() {
        // Clear all cached data
        foodCache.clear();
        cacheTimetamps.clear();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Shutdown executor service when ViewModel is cleared
        executorService.shutdown();

        // Clear cache when ViewModel is cleared
        invalidateAllCache();
    }
}
