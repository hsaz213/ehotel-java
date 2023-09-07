package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class BuffetServiceImpl implements BuffetService {

    @Override
    public Buffet refill(Buffet buffet, List<Meal> meals) {
//        LocalDateTime
        return buffet;
    }

    @Override
    public boolean consumeFreshest(Buffet buffet, MealType mealType) {
        return false;
    }

    @Override
    public int collectWaste(Buffet buffet) {
        int totalDiscardedCost = calculateTotalDiscardedCost(buffet.getMeals());
        removeExpiredMeals(buffet);
        return totalDiscardedCost;
    }

    private int calculateTotalDiscardedCost(List<Meal> meals) {
        return meals.stream()
                .filter(meal -> meal.getMealType().getDurability() == MealDurability.SHORT)
                .filter(meal -> ChronoUnit.MINUTES.between(meal.getTimeStamp(), LocalDateTime.now()) > 90)
                .mapToInt(meal -> meal.getAmount() * meal.getMealType().getCost()).sum();
    }

    private void removeExpiredMeals(Buffet buffet) {
        System.out.println("Before removal: " + buffet.getMeals().toString());


        List<Meal> filteredMeals = buffet.getMeals().stream()
                .filter(meal -> !(meal.getMealType().getDurability() == MealDurability.SHORT &&
                        ChronoUnit.MINUTES.between(meal.getTimeStamp(), LocalDateTime.now()) > 90))
                .collect(Collectors.toList());

        buffet.setMeals(filteredMeals);

        System.out.println("After removal: " + buffet.getMeals().toString());
    }

}
