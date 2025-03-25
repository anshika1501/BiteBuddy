package com.anshu.bitebuddy.core.database.interaction;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.core.database.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;


public class FirebaseInteraction {

    private static final String TAG = "FirebaseInteraction";

    private static final String USER_DATABASE_PATH = "User";
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseInteraction(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }


    public void insertUser(User user, Consumer<Exception> onUserAdded) {
        var ref = firebaseFirestore.collection(USER_DATABASE_PATH).document(user.getUid());
        ref.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                onUserAdded.accept(null);
            } else {
                addUserToDatabase(user, onUserAdded, ref);
            }
        }).addOnFailureListener(e -> addUserToDatabase(user, onUserAdded, ref));
    }

    private static void addUserToDatabase(User user, Consumer<Exception> onUserAdded, DocumentReference ref) {
        ref.set(user).addOnSuccessListener(aVoid -> {
            onUserAdded.accept(null);
        }).addOnFailureListener(onUserAdded::accept);
    }

    public void getUser(BiConsumer<User, Exception> onUserRetrieved) {
        if (firebaseAuth.getUid() == null) {
            onUserRetrieved.accept(null, new Exception("User not logged in"));
            return;
        }
        firebaseFirestore.collection(USER_DATABASE_PATH).document(firebaseAuth.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            onUserRetrieved.accept(user, null);
        }).addOnFailureListener(e -> {
            onUserRetrieved.accept(null, e);
        });
    }

    public void logOut(Consumer<Void> onLoggedOut) {
        firebaseAuth.signOut();
        onLoggedOut.accept(null);
    }

    public static enum FoodType {
        ALL,
        Breakfast,
        Lunch,
        Dinner,
        Snacks
    }

    public LiveData<List<Food>> getFoodData(FoodType foodType) {
        if (foodType == FoodType.ALL) {
            return mergeFoodLiveData(
                    getFoodDataInternal(FoodType.Breakfast),
                    getFoodDataInternal(FoodType.Lunch),
                    getFoodDataInternal(FoodType.Dinner),
                    getFoodDataInternal(FoodType.Snacks)
            );
        } else {
            return getFoodDataInternal(foodType);
        }
    }


    private LiveData<List<Food>> mergeFoodLiveData(
            LiveData<List<Food>> liveData1,
            LiveData<List<Food>> liveData2,
            LiveData<List<Food>> liveData3,
            LiveData<List<Food>> liveData4
    ) {
        MediatorLiveData<List<Food>> mergedLiveData = new MediatorLiveData<>();
        List<Food> combinedList = new ArrayList<>();

        Observer<List<Food>> observer = newList -> {
            if (newList != null) {
                combinedList.addAll(newList);
            }
            mergedLiveData.setValue(new ArrayList<>(combinedList)); // Update LiveData
        };

        mergedLiveData.addSource(liveData1, observer);
        mergedLiveData.addSource(liveData2, observer);
        mergedLiveData.addSource(liveData3, observer);
        mergedLiveData.addSource(liveData4, observer);

        return mergedLiveData;
    }


    private LiveData<List<Food>> getFoodDataInternal(FoodType foodType) {
        MutableLiveData<List<Food>> liveData = new MutableLiveData<>();

        var ref = firebaseFirestore.collection("food")
                .document("indian")
                .collection(foodType.name());

        ref.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e(TAG, "Error fetching food data", error);
                liveData.setValue(Collections.emptyList());
                return;
            }

            if (value != null && !value.isEmpty()) {
                List<Food> foodList = new ArrayList<>();
                for (DocumentSnapshot doc : value.getDocuments()) {
                    Food food = doc.toObject(Food.class);
                    if (food != null) {
                        foodList.add(food);
                    }
                }
                liveData.setValue(foodList);
            } else {
                liveData.setValue(Collections.emptyList());
            }
        });

        return liveData;
    }
}

// BiteBuddy/user/userId/{data}
