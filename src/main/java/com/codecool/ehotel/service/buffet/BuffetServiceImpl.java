package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BuffetServiceImpl implements BuffetService {

    private int totalCost = 0;
    private int unhappyGuestCount = 0;

    private static final int SHORT_EXPIRY_MINUTES = 90;

    // REFILL
    @Override
    public Buffet refill(Buffet buffet, List<Meal> meals, LocalDateTime currentCycleStart) {
        List<Meal> buffetMeals = new ArrayList<>(buffet.getMeals());
        for (Meal meal : meals) {
            Meal newMeal = new Meal(meal.getMealType(), meal.getAmount(), currentCycleStart);
            buffetMeals.add(newMeal);
        }
        buffet.setMeals(buffetMeals);
        System.out.println("🏆 refilled buffet meals: " + buffet.getMeals());
        return buffet;
    }

    // EAT
    @Override
    public boolean consumeFreshest(Buffet buffet, MealType mealType) {
        List<Meal> filteredMeals = buffet.findPortions(mealType);

        if (!filteredMeals.isEmpty()) {
            Meal freshestBatchOfMeal = filteredMeals.get(filteredMeals.size() - 1);

            Meal updatedMeal = new Meal(freshestBatchOfMeal.getMealType(), freshestBatchOfMeal.getAmount() - 1, freshestBatchOfMeal.getTimeStamp());

            System.out.println("buffet before eating: " + buffet.getMeals());
            if (updatedMeal.getAmount() > 0) {
                buffet.getMeals().remove(freshestBatchOfMeal);
                buffet.getMeals().add(updatedMeal);
                System.out.println("✅ buffet after eating: " + buffet.getMeals());
                return true;

            } else if (updatedMeal.getAmount() == 0) {
                // remove meal if amount is zero
                buffet.getMeals().remove(freshestBatchOfMeal);
                System.out.println("❌ out of " + updatedMeal.getMealType());
                unhappyGuestCount++;
                logBuffetContent(buffet);
                return false;
            }
        }
        System.out.println("❌ chosen meal not in buffet");
        unhappyGuestCount++;
        logBuffetContent(buffet);

        return false;
    }

    public void logBuffetContent(Buffet buffet) {
        System.out.println("Buffet content: " + buffet.getMeals());
    }

    public void chooseAndEat(Buffet buffet, int cycleIndex, List<List<Guest>> guestsGroups) {
        List<Guest> currentGroup = guestsGroups.get(cycleIndex);

        ArrayList<MealType> chosenMealTypes = new ArrayList<MealType>();

        for (Guest guest :
                currentGroup) {
            List<MealType> guestPreferredMeals = guest.guestType().getMealPreferences();

            // add random
            int randomIndex = getRandomIndex(guestPreferredMeals.size());
            MealType chosenMealType = guestPreferredMeals.get(randomIndex);

            System.out.println("🥸guest chose a " + chosenMealType);
            chosenMealTypes.add(chosenMealType);
        }
//        System.out.println("🤖meal types list: " + chosenMealTypes);

        for (MealType mealType :
                chosenMealTypes) {
            consumeFreshest(buffet, mealType);
        }
    }

    private int getRandomIndex(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }


    // REMOVE EXPIRED
    @Override
    public int collectWaste(Buffet buffet, LocalDateTime currentCycleEnd) {
        int cycleDiscardedCost = calculateCycleDiscardedCost(buffet.getMeals(), currentCycleEnd);
        removeExpiredMeals(buffet, currentCycleEnd);

        totalCost += cycleDiscardedCost;

        System.out.println("cost of discarded meals in this cycle: " + cycleDiscardedCost + " 💵");

        return cycleDiscardedCost;

    }

    private int calculateCycleDiscardedCost(List<Meal> meals, LocalDateTime currentCycleEnd) {

        return meals.stream()
                .filter(meal -> meal.getMealType().getDurability() == MealDurability.SHORT)
                .filter(meal -> ChronoUnit.MINUTES.between(meal.getTimeStamp(), currentCycleEnd) >= SHORT_EXPIRY_MINUTES)
                .mapToInt(meal -> meal.getAmount() * meal.getMealType().getCost()).sum();
    }

    private void removeExpiredMeals(Buffet buffet, LocalDateTime currentCycleEnd) {

        System.out.println("🤖 Before removing expired meals: " + buffet.getMeals().toString());

        int shortDurationExpiry = 90;

        List<Meal> filteredMeals = buffet.getMeals().stream()
                .filter(meal -> !(meal.getMealType().getDurability() == MealDurability.SHORT &&
                        ChronoUnit.MINUTES.between(meal.getTimeStamp(), currentCycleEnd) >= SHORT_EXPIRY_MINUTES))
                .collect(Collectors.toList());

        buffet.setMeals(filteredMeals);

        System.out.println("🤖 After removing expired meals: " + buffet.getMeals().toString());
    }


    public void cycleManager(Buffet buffet, List<Meal> meals, List<List<Guest>> guestsGroups) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayBreakfastStart = LocalDateTime.of(today, midnight).plusHours(6);
        System.out.println("today's bf starts at: " + todayBreakfastStart);

        LocalDateTime currentCycleStart;
        LocalDateTime currentCycleEnd;

        int cycleLengthMinutes = 30;
        int numOfCycles = 8;

        for (int cycleIndex = 0; cycleIndex < numOfCycles; cycleIndex++) {
            currentCycleStart = todayBreakfastStart.plusMinutes(cycleIndex * cycleLengthMinutes);
            currentCycleEnd = currentCycleStart.plusMinutes(cycleLengthMinutes);

            System.out.println("current cycle start: " + currentCycleStart);
            System.out.println("current cycle end: " + currentCycleEnd);

            List<Meal> timestampedMeals = new ArrayList<>();
            for(Meal meal : meals) {
                Meal timeStampedMeal = new Meal(meal.getMealType(), meal.getAmount(), currentCycleStart);
                timestampedMeals.add(timeStampedMeal);
            }

            refill(buffet, timestampedMeals, currentCycleStart);

            chooseAndEat(buffet, cycleIndex, guestsGroups);

            collectWaste(buffet, currentCycleEnd);

        }
            System.out.println("Total cost of discarded food: " + totalCost + " 💵");
            System.out.println("Total number of unhappy guests: " + unhappyGuestCount + " ☹️");
    }
}
