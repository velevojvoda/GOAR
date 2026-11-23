package com.example.goar.data.repository;

import com.example.goar.data.model.Allergen;
import com.example.goar.data.model.DietType;
import com.example.goar.data.model.DishType;
import com.example.goar.data.model.Ingredient;
import com.example.goar.data.model.IngredientCategory;
import com.example.goar.data.model.IngredientType;
import com.example.goar.data.model.MealType;
import com.example.goar.data.model.MeasurementUnit;
import com.example.goar.data.model.Origin;
import com.example.goar.data.model.PreparationType;
import com.example.goar.data.model.Recipe;
import com.example.goar.data.model.RecipeIngredient;
import com.example.goar.data.model.RecipeStep;
import com.example.goar.data.model.Role;
import com.example.goar.data.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MockData {
    // true = admin, false = regular user
    public static final boolean CURRENT_USER_IS_ADMIN = true;

    private MockData() {
    }

    public static final User ADMIN = new User("1", "admin", "admin@goar.com", "Vojvodina", Role.ADMIN);
    public static final User ANA = new User("2", "ana", "ana@goar.com", "Vojvodina", Role.USER);
    public static final User MARKO = new User("3", "marko", "marko@goar.com", "Sicily", Role.USER);

    public static User getCurrentUser() {
        return CURRENT_USER_IS_ADMIN ? ADMIN : ANA;
    }

    public static List<User> getUsers() {
        return Arrays.asList(ADMIN, ANA, MARKO);
    }

    public static final MealType BREAKFAST = new MealType("1", "Breakfast");
    public static final MealType LUNCH = new MealType("2", "Lunch");
    public static final MealType DINNER = new MealType("3", "Dinner");
    public static final MealType MAIN_COURSE = new MealType("4", "Main course");
    public static final MealType APPETIZER = new MealType("5", "Appetizer");
    public static final MealType SALAD = new MealType("6", "Salad");
    public static final MealType DESSERT = new MealType("7", "Dessert");
    public static final MealType DRINK = new MealType("8", "Drink");

    public static List<MealType> getMealTypes() {
        return Arrays.asList(BREAKFAST, LUNCH, DINNER, MAIN_COURSE, APPETIZER, SALAD, DESSERT, DRINK);
    }

    public static final DishType SOUP = new DishType("1", "Soup");
    public static final DishType STEW = new DishType("2", "Stew");
    public static final DishType PASTA = new DishType("3", "Pasta");
    public static final DishType PIZZA = new DishType("4", "Pizza");
    public static final DishType SANDWICH = new DishType("5", "Sandwich");
    public static final DishType BURGER = new DishType("6", "Burger");
    public static final DishType RISOTTO = new DishType("7", "Risotto");
    public static final DishType MEAT_DISH = new DishType("8", "Meat dish");
    public static final DishType FISH_DISH = new DishType("9", "Fish dish");
    public static final DishType VEGETABLE_DISH = new DishType("10", "Vegetable dish");
    public static final DishType BAKERY = new DishType("11", "Bakery product");
    public static final DishType PASTRY = new DishType("12", "Pastry");
    public static final DishType CAKE = new DishType("13", "Cake");
    public static final DishType PANCAKES = new DishType("14", "Pancakes");

    public static List<DishType> getDishTypes() {
        return Arrays.asList(SOUP, STEW, PASTA, PIZZA, SANDWICH, BURGER, RISOTTO, MEAT_DISH,
                FISH_DISH, VEGETABLE_DISH, BAKERY, PASTRY, CAKE, PANCAKES);
    }

    public static final DietType VEGETARIAN = new DietType("1", "Vegetarian");
    public static final DietType VEGAN = new DietType("2", "Vegan");
    public static final DietType LENTEN = new DietType("3", "Lenten");
    public static final DietType NON_LENTEN = new DietType("4", "Non-lenten");
    public static final DietType CHRONO = new DietType("5", "Chrono");
    public static final DietType PESCATARIAN = new DietType("6", "Pescatarian");

    public static List<DietType> getDietTypes() {
        return Arrays.asList(VEGETARIAN, VEGAN, LENTEN, NON_LENTEN, CHRONO, PESCATARIAN);
    }

    public static final IngredientCategory VEGETABLES = new IngredientCategory("1", "Vegetables");
    public static final IngredientCategory FRUIT = new IngredientCategory("2", "Fruit");
    public static final IngredientCategory MEAT = new IngredientCategory("3", "Meat");
    public static final IngredientCategory FISH = new IngredientCategory("4", "Fish");
    public static final IngredientCategory DAIRY = new IngredientCategory("5", "Dairy");
    public static final IngredientCategory GRAINS = new IngredientCategory("6", "Grains");
    public static final IngredientCategory SPICES = new IngredientCategory("7", "Spices");
    public static final IngredientCategory NUTS = new IngredientCategory("8", "Nuts");

    public static List<IngredientCategory> getIngredientCategories() {
        return Arrays.asList(VEGETABLES, FRUIT, MEAT, FISH, DAIRY, GRAINS, SPICES, NUTS);
    }

    public static final Allergen GLUTEN = new Allergen("1", "Gluten");
    public static final Allergen MILK_ALLERGEN = new Allergen("2", "Milk");
    public static final Allergen EGGS_ALLERGEN = new Allergen("3", "Eggs");
    public static final Allergen PEANUTS_ALLERGEN = new Allergen("4", "Peanuts");
    public static final Allergen FISH_ALLERGEN = new Allergen("5", "Fish");

    public static List<Allergen> getAllergens() {
        return Arrays.asList(GLUTEN, MILK_ALLERGEN, EGGS_ALLERGEN, PEANUTS_ALLERGEN, FISH_ALLERGEN);
    }

    public static final Ingredient FLOUR = ingredient("1", "Flour", GRAINS, MeasurementUnit.GRAM, GLUTEN);
    public static final Ingredient MILK = ingredient("2", "Milk", DAIRY, MeasurementUnit.MILLILITER, MILK_ALLERGEN);
    public static final Ingredient EGG = ingredient("3", "Egg", DAIRY, MeasurementUnit.PIECE, EGGS_ALLERGEN);
    public static final Ingredient PEANUTS = ingredient("4", "Peanuts", NUTS, MeasurementUnit.GRAM, PEANUTS_ALLERGEN);
    public static final Ingredient TOMATO = ingredient("5", "Tomato", VEGETABLES, MeasurementUnit.PIECE);
    public static final Ingredient PASTA_INGREDIENT = ingredient("6", "Spaghetti", GRAINS, MeasurementUnit.GRAM, GLUTEN);
    public static final Ingredient SALT = ingredient("7", "Salt", SPICES, MeasurementUnit.GRAM);
    public static final Ingredient PEPPER = ingredient("8", "Black pepper", SPICES, MeasurementUnit.GRAM);
    public static final Ingredient CHICKEN = ingredient("9", "Chicken breast", MEAT, MeasurementUnit.GRAM);
    public static final Ingredient SALMON = ingredient("10", "Salmon", FISH, MeasurementUnit.GRAM, FISH_ALLERGEN);
    public static final Ingredient APPLE = ingredient("11", "Apple", FRUIT, MeasurementUnit.PIECE);
    public static final Ingredient YOGURT_BRANDED = ingredient("12", "Imlek Greek yogurt", DAIRY, MeasurementUnit.GRAM, MILK_ALLERGEN);

    static {
        SALT.setQuantityOptional(true);
        PEPPER.setQuantityOptional(true);
        YOGURT_BRANDED.setType(IngredientType.BRANDED);
    }

    public static List<Ingredient> getIngredients() {
        return Arrays.asList(FLOUR, MILK, EGG, PEANUTS, TOMATO, PASTA_INGREDIENT, SALT, PEPPER,
                CHICKEN, SALMON, APPLE, YOGURT_BRANDED);
    }

    private static Ingredient ingredient(String id, String name, IngredientCategory category,
                                         MeasurementUnit unit, Allergen... allergens) {
        Ingredient ingredient = new Ingredient(id, name, category, unit, IngredientType.GENERIC);
        ingredient.setAllergens(new ArrayList<>(Arrays.asList(allergens)));
        return ingredient;
    }

    private static List<Recipe> recipes;

    public static List<Recipe> getRecipes() {
        if (recipes == null) {
            recipes = createRecipes();
        }
        return recipes;
    }

    public static Recipe getRecipeById(String id) {
        for (Recipe recipe : getRecipes()) {
            if (recipe.getId().equals(id)) return recipe;
        }
        return null;
    }

    private static List<Recipe> createRecipes() {
        List<Recipe> list = new ArrayList<>();

        Recipe bread = new Recipe("1", "Homemade bread", ANA, BREAKFAST, BAKERY, NON_LENTEN, 4, 50);
        bread.getIngredients().add(new RecipeIngredient(FLOUR, 500.0, MeasurementUnit.GRAM));
        bread.getIngredients().add(new RecipeIngredient(MILK, 250.0, MeasurementUnit.MILLILITER));
        bread.getIngredients().add(new RecipeIngredient(EGG, 3.0, MeasurementUnit.PIECE));
        bread.getIngredients().add(new RecipeIngredient(SALT, null, MeasurementUnit.GRAM));
        bread.getSteps().add(new RecipeStep(1, "Prepare the dough",
                "Mix flour, eggs and milk until combined.", PreparationType.NO_HEAT, 5));
        bread.getSteps().add(new RecipeStep(2, "Let the dough rest",
                "Leave the dough to rest for 20 minutes.", PreparationType.NO_HEAT, 20));
        bread.getSteps().add(new RecipeStep(3, "Bake the bread",
                "Grease the baking tray and bake for 25 minutes at 200 degrees.", PreparationType.BAKING, 25));
        bread.setOrigin(new Origin("Serbia", "Vojvodina", "Novi Sad"));
        bread.setStory("My grandmother's recipe, made every Sunday morning.");
        bread.setAverageRating(4.6);
        bread.setRatingCount(12);
        list.add(bread);

        Recipe pasta = new Recipe("2", "Spaghetti al pomodoro", MARKO, LUNCH, PASTA, LENTEN, 2, 25);
        pasta.getIngredients().add(new RecipeIngredient(PASTA_INGREDIENT, 200.0, MeasurementUnit.GRAM));
        pasta.getIngredients().add(new RecipeIngredient(TOMATO, 4.0, MeasurementUnit.PIECE));
        pasta.getIngredients().add(new RecipeIngredient(SALT, null, MeasurementUnit.GRAM));
        pasta.getSteps().add(new RecipeStep(1, "Cook the pasta",
                "Cook the spaghetti in salted water.", PreparationType.BOILING, 10));
        pasta.getSteps().add(new RecipeStep(2, "Make the sauce",
                "Braise chopped tomatoes until soft.", PreparationType.BRAISING, 15));
        pasta.setOrigin(new Origin("Italy", "Sicily", "Palermo"));
        pasta.setAverageRating(4.2);
        pasta.setRatingCount(8);
        list.add(pasta);

        Recipe pancakes = new Recipe("3", "Pancakes", ANA, DESSERT, PANCAKES, NON_LENTEN, 4, 30);
        pancakes.getIngredients().add(new RecipeIngredient(FLOUR, 250.0, MeasurementUnit.GRAM));
        pancakes.getIngredients().add(new RecipeIngredient(MILK, 500.0, MeasurementUnit.MILLILITER));
        pancakes.getIngredients().add(new RecipeIngredient(EGG, 2.0, MeasurementUnit.PIECE));
        pancakes.getSteps().add(new RecipeStep(1, "Make the batter",
                "Whisk flour, milk and eggs.", PreparationType.NO_HEAT, null));
        pancakes.getSteps().add(new RecipeStep(2, "Fry",
                "Fry each pancake on both sides.", PreparationType.FRYING, 20));
        pancakes.setOrigin(new Origin("Serbia", null, null));
        pancakes.setAverageRating(4.8);
        pancakes.setRatingCount(20);
        list.add(pancakes);

        Recipe salmon = new Recipe("4", "Grilled salmon", MARKO, DINNER, FISH_DISH, PESCATARIAN, 2, 20);
        salmon.getIngredients().add(new RecipeIngredient(SALMON, 400.0, MeasurementUnit.GRAM));
        salmon.getIngredients().add(new RecipeIngredient(PEPPER, null, MeasurementUnit.GRAM));
        salmon.getSteps().add(new RecipeStep(1, "Grill",
                "Grill the salmon 5 minutes on each side.", PreparationType.GRILLING, 10));
        salmon.setOrigin(new Origin("Norway", null, "Bergen"));
        list.add(salmon);

        Recipe veganPancakes = new Recipe("5", "Vegan pancakes", MARKO, DESSERT, PANCAKES, VEGAN, 4, 30);
        veganPancakes.getIngredients().add(new RecipeIngredient(FLOUR, 250.0, MeasurementUnit.GRAM));
        veganPancakes.getSteps().add(new RecipeStep(1, "Make the batter",
                "Whisk flour with plant milk.", PreparationType.NO_HEAT, null));
        veganPancakes.setOriginalRecipeId("3");
        veganPancakes.setModificationNote("Plant milk instead of milk and no eggs.");
        list.add(veganPancakes);

        return Collections.unmodifiableList(list);
    }
}
