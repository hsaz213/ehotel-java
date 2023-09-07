package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuffetServiceImpl implements BuffetService {

    @Override
    public Buffet refill(Buffet buffet, List<Meal> meals, LocalDateTime currentCycleStart) {

        List<Meal> buffetMeals = new ArrayList<>(buffet.getMeals());

        for (Meal meal :
                meals) {
            Meal newMeal = new Meal(meal.getMealType(), meal.getAmount(), currentCycleStart);
            buffetMeals.add(newMeal);
        }

        System.out.println("🏆 refilled buffet meals: " + buffetMeals);

        buffet.setMeals(buffetMeals);

        return buffet;
    }

    @Override
    public boolean consumeFreshest(Buffet buffet, MealType mealType) {
        List<Meal> filteredMeals = buffet.findPortions(mealType);
        if (!filteredMeals.isEmpty()) {
            System.out.println("❌: " + filteredMeals.get(filteredMeals.size() - 1));
            buffet.removeMeal(filteredMeals.get(filteredMeals.size() - 1));
            return true;
        }
        return false;
    }

    @Override
    public int collectWaste(Buffet buffet) {
        int totalDiscardedCost = calculateTotalDiscardedCost(buffet.getMeals());
        removeExpiredMeals(buffet);

        System.out.println("discarded 💵💵: " + totalDiscardedCost);

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

    public void cycleManager(Buffet buffet, List<Meal> meals, MealType mealType) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayBreakfastStart = LocalDateTime.of(today, midnight).plusHours(6);
        System.out.println("today's bf starts at: " + todayBreakfastStart);
        LocalDateTime currentCycleStart;
        int cycleLengthMinutes = 30;
        int numOfCycles = 8;

        for (int cycleIndex = 0; cycleIndex < numOfCycles; cycleIndex++) {
            currentCycleStart = todayBreakfastStart.plusMinutes(cycleIndex * cycleLengthMinutes);
            System.out.println("current cycle start: " + currentCycleStart);

            refill(buffet, meals, currentCycleStart);

            consumeFreshest(buffet, mealType);

            collectWaste(buffet);

        }
    }
}
