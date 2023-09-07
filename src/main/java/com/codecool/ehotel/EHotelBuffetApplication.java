package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestProvider;
import com.codecool.ehotel.service.BreakfastGroups;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.codecool.ehotel.model.MealType.SCRAMBLED_EGGS;
import static java.lang.Math.floor;

import java.time.LocalDate;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services


        // Generate guests for the season
        GuestProvider guestProvider = new GuestProvider();

        LocalDate seasonStart = LocalDate.of(2023, 8, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 8, 8);
        List<Guest> guests = guestProvider.generateGuests(100, seasonStart, seasonEnd);

        // Get guests for today
        LocalDate current = LocalDate.of(2023, 8, 5);
        Set<Guest> guestsForToday = guestProvider.getGuestsForDay(guests, current);

        // Print guests for today
//        System.out.println("Guests for today are: ");
//        for (Guest guest : guestsForToday) {
//            System.out.println(guest);
//        }

//        System.out.println("Number of guests today: " + guests.size());

        BreakfastGroups breakfastGroup = new BreakfastGroups();

        ArrayList<List<Guest>> groups = breakfastGroup.createGroups(guestsForToday);

//        System.out.println(groups);

//        System.out.println(Buffet.findPortions(SCRAMBLED_EGGS));


        // Initialize meals and buffet
        Meal scrambledEggs = new Meal(MealType.SCRAMBLED_EGGS, 10, LocalDateTime.now().minusMinutes(80)); // these should not be removed because they were added less than 90 minutes before ✅
        Meal croissant = new Meal(MealType.CROISSANT, 10, LocalDateTime.now().minusMinutes(200)); // these should be removed because they were added more than 90 minutes before ✅


        List<Meal> meals = new ArrayList<>(Arrays.asList(scrambledEggs, croissant));
        Buffet buffet = new Buffet();

        buffet.setMeals(meals);

        List<Meal> buffetMeals = buffet.getMeals();

        Meal muffin = new Meal(MealType.MUFFIN, 10, LocalDateTime.now().minusMinutes(120)); // these should not be removed because they were added less than 90 minutes before ✅
        Meal pancake = new Meal(MealType.PANCAKE, 10, LocalDateTime.now().minusMinutes(120)); // these should be removed because they were added more than 90 minutes before ✅


        List<Meal> secondsMeals = new ArrayList<>(Arrays.asList(muffin, pancake));

        System.out.println("👋 buffet meals:" + buffetMeals);

        buffetMeals.addAll(secondsMeals);

        System.out.println("👋✅🥸 buffet meals after second fill:" + buffetMeals);

        // Initialize service
        BuffetServiceImpl buffetService = new BuffetServiceImpl();

        // Collect waste and print the cost
        int totalWasteCost = buffetService.collectWaste(buffet);
        System.out.println("cost of discarded meals: " + totalWasteCost);


    }
}