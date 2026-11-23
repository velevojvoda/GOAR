package com.example.goar.data.repository;

import android.util.Log;

import com.example.goar.data.model.Allergen;
import com.example.goar.data.model.DietType;
import com.example.goar.data.model.DishType;
import com.example.goar.data.model.Ingredient;
import com.example.goar.data.model.IngredientCategory;
import com.example.goar.data.model.MealType;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FirestoreSeeder {
    private static final String TAG = "FirestoreSeeder";

    private FirestoreSeeder() {
    }

    public static void seedIfEmpty() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("mealTypes").limit(1).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.isEmpty()) {
                        seed(db);
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Check failed", e));
    }

    private static void seed(FirebaseFirestore db) {
        WriteBatch batch = db.batch();

        for (MealType type : MockData.getMealTypes()) {
            addNamed(db, batch, "mealTypes", type.getId(), type.getName());
        }
        for (DishType type : MockData.getDishTypes()) {
            addNamed(db, batch, "dishTypes", type.getId(), type.getName());
        }
        for (DietType type : MockData.getDietTypes()) {
            addNamed(db, batch, "dietTypes", type.getId(), type.getName());
        }
        for (IngredientCategory category : MockData.getIngredientCategories()) {
            addNamed(db, batch, "ingredientCategories", category.getId(), category.getName());
        }
        for (Allergen allergen : MockData.getAllergens()) {
            addNamed(db, batch, "allergens", allergen.getId(), allergen.getName());
        }
        for (Ingredient ingredient : MockData.getIngredients()) {
            batch.set(db.collection("ingredients").document(ingredient.getId()), toMap(ingredient));
        }

        batch.commit()
                .addOnSuccessListener(unused -> Log.d(TAG, "Seed completed"))
                .addOnFailureListener(e -> Log.e(TAG, "Seed failed", e));
    }

    private static void addNamed(FirebaseFirestore db, WriteBatch batch, String collection,
                                 String id, String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        batch.set(db.collection(collection).document(id), data);
    }

    private static Map<String, Object> toMap(Ingredient ingredient) {
        List<String> allergenIds = new ArrayList<>();
        for (Allergen allergen : ingredient.getAllergens()) {
            allergenIds.add(allergen.getId());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("name", ingredient.getName());
        data.put("categoryId", ingredient.getCategory().getId());
        data.put("defaultUnit", ingredient.getDefaultUnit().name());
        data.put("quantityOptional", ingredient.isQuantityOptional());
        data.put("type", ingredient.getType().name());
        data.put("allergenIds", allergenIds);
        return data;
    }
}
