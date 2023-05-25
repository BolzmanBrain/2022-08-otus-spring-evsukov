package ru.otus.spring.strategy;

import ru.otus.spring.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class NutritionalValueComputationStrategyImpl implements NutritionalValueComputationStrategy {
    private static final double WEIGHT_100G = 100.0;
    @Override
    public NutritionalValue compute(Course course) {
        var quantity = course.getQuantity();
        var food = course.getFood();
        var nutrientStorageType = food.getNutrientStorageType();

        var calories = computeNutrient(food.getCalories(), quantity, nutrientStorageType);
        var proteinsInGrams = computeNutrient(food.getProteinsInGrams(), quantity, nutrientStorageType);
        var fatsInGrams = computeNutrient(food.getFatsInGrams(), quantity, nutrientStorageType);
        var carbsInGrams = computeNutrient(food.getCarbsInGrams(), quantity, nutrientStorageType);
        return new NutritionalValue(calories, proteinsInGrams, fatsInGrams, carbsInGrams);
    }

    @Override
    public NutritionalValue compute(Meal meal) {
        var mealNutririonalValues = meal.getCourses().stream()
                .map(this::compute).toList();

        return add(mealNutririonalValues);
    }

    @Override
    public NutritionalValue compute(Diet diet) {
        var dietNutririonalValues = diet.getMeals().stream()
                .map(this::compute).toList();

        return add(dietNutririonalValues);
    }

    private NutritionalValue add(List<NutritionalValue> nutritionalValues) {
        double totalCalories = 0;
        double totalProteinsInGrams = 0;
        double totalFatsInGrams = 0;
        double totalCarbsInGrams = 0;

        for(var nutritionalValue: nutritionalValues) {
            totalCalories += nutritionalValue.getCalories();;
            totalProteinsInGrams += nutritionalValue.getProteinsInGrams();
            totalFatsInGrams += nutritionalValue.getFatsInGrams();
            totalCarbsInGrams += nutritionalValue.getCarbsInGrams();
        }
        totalCalories = round(totalCalories);
        totalProteinsInGrams = round(totalProteinsInGrams);
        totalFatsInGrams = round(totalFatsInGrams);
        totalCarbsInGrams = round(totalCarbsInGrams);
        return new NutritionalValue(totalCalories, totalProteinsInGrams, totalFatsInGrams, totalCarbsInGrams);
    }

    private double computeNutrient(double amountOfNutrientInFood, double foodQuantity, NutrientStorageType nutrientStorageType) {
        double nutrient = 0;

        if(nutrientStorageType.getCode().equals(NutrientStorageType.IN_SERVING)) {
            nutrient = foodQuantity * amountOfNutrientInFood;
        } else {
            nutrient = (foodQuantity / WEIGHT_100G) * amountOfNutrientInFood;
        }
        return round(nutrient);
    }

    private double round(double value) {
        BigDecimal roundedValue = new BigDecimal(value).setScale(2, RoundingMode.HALF_DOWN);
        return roundedValue.doubleValue();
    }
}
