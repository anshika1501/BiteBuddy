package com.anshu.bitebuddy.core.database.interaction;


import android.util.Log;

import com.anshu.bitebuddy.core.database.model.Food;
import com.anshu.bitebuddy.core.database.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

    public enum FoodType {
        ALL,
        Breakfast,
        Lunch,
        Dinner,
        Snacks
    }


    public void getFoodData(FoodType foodType, Consumer<List<Food>> onDataReceived, Consumer<Throwable> onError) {
        if (foodType == FoodType.ALL) {
            mergeFoodData(
                    onDataReceived,
                    onError,
                    FoodType.Breakfast,
                    FoodType.Lunch,
                    FoodType.Dinner,
                    FoodType.Snacks
            );
        } else {
            getFoodDataInternal(foodType, onDataReceived, onError);
        }
    }

    private void mergeFoodData(
            Consumer<List<Food>> onDataReceived,
            Consumer<Throwable> onError,
            FoodType... foodTypes
    ) {
        List<Food> combinedList = new ArrayList<>();
        AtomicInteger sourcesRemaining = new AtomicInteger(foodTypes.length);

        for (FoodType foodType : foodTypes) {
            getFoodDataInternal(foodType,
                    newList -> {
                        synchronized (combinedList) {
                            if (newList != null) {
                                combinedList.addAll(newList);
                            }

                            // When all sources have provided data, shuffle and return
                            if (sourcesRemaining.decrementAndGet() == 0) {
                                Collections.shuffle(combinedList);
                                onDataReceived.accept(new ArrayList<>(combinedList));
                            }
                        }
                    },
                    onError
            );
        }
    }

    private void getFoodDataInternal(
            FoodType foodType,
            Consumer<List<Food>> onDataReceived,
            Consumer<Throwable> onError
    ) {
        var ref = firebaseFirestore.collection("food")
                .document("indian")
                .collection(foodType.name());

        ref.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                List<Food> foodList = new ArrayList<>();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    Food food = doc.toObject(Food.class);
                    if (food != null) {
                        foodList.add(food);
                    }
                }
                onDataReceived.accept(foodList);
            } else {
                onDataReceived.accept(Collections.emptyList());
            }
        }).addOnFailureListener(error -> {
            Log.e(TAG, "Error fetching food data", error);
            onError.accept(error);
        });
    }

//    public LiveData<List<Food>> getFoodData(FoodType foodType) {
//        if (foodType == FoodType.ALL) {
//            return mergeFoodLiveData(
//                    getFoodDataInternal(FoodType.Breakfast),
//                    getFoodDataInternal(FoodType.Lunch),
//                    getFoodDataInternal(FoodType.Dinner),
//                    getFoodDataInternal(FoodType.Snacks)
//            );
//        } else {
//            return getFoodDataInternal(foodType);
//        }
//    }
//
//
//    private LiveData<List<Food>> mergeFoodLiveData(
//            LiveData<List<Food>> liveData1,
//            LiveData<List<Food>> liveData2,
//            LiveData<List<Food>> liveData3,
//            LiveData<List<Food>> liveData4
//    ) {
//        MediatorLiveData<List<Food>> mergedLiveData = new MediatorLiveData<>();
//        List<Food> combinedList = new ArrayList<>();
//        Observer<List<Food>> observer = getListObserver(combinedList, mergedLiveData);
//
//        mergedLiveData.addSource(liveData1, observer);
//        mergedLiveData.addSource(liveData2, observer);
//        mergedLiveData.addSource(liveData3, observer);
//        mergedLiveData.addSource(liveData4, observer);
//
//        return mergedLiveData;
//    }
//
//    @NonNull
//    private static Observer<List<Food>> getListObserver(List<Food> combinedList, MediatorLiveData<List<Food>> mergedLiveData) {
//        AtomicInteger sourcesRemaining = new AtomicInteger(4); // Track updates from all sources
//        return
//                newList -> {
//                    if (newList != null) {
//                        combinedList.addAll(newList);
//                    }
//
//                    // When all sources have provided data, shuffle and update LiveData
//                    if (sourcesRemaining.decrementAndGet() == 0) {
//                        Collections.shuffle(combinedList);
//                        mergedLiveData.setValue(new ArrayList<>(combinedList)); // Update LiveData
//                    }
//                };
//    }
//
//
//    private LiveData<List<Food>> getFoodDataInternal(FoodType foodType) {
//        MutableLiveData<List<Food>> liveData = new MutableLiveData<>();
//
//        var ref = firebaseFirestore.collection("food")
//                .document("indian")
//                .collection(foodType.name());
//
//        ref.addSnapshotListener((value, error) -> {
//            if (error != null) {
//                Log.e(TAG, "Error fetching food data", error);
//                liveData.setValue(Collections.emptyList());
//                return;
//            }
//
//            if (value != null && !value.isEmpty()) {
//                List<Food> foodList = new ArrayList<>();
//                for (DocumentSnapshot doc : value.getDocuments()) {
//                    Food food = doc.toObject(Food.class);
//                    if (food != null) {
//                        foodList.add(food);
//                    }
//                }
//                liveData.setValue(foodList);
//            } else {
//                liveData.setValue(Collections.emptyList());
//            }
//        });
//
//        return liveData;
//    }
}

// BiteBuddy/user/userId/{data}
