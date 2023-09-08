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

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services


        // Generate guests for the season
        GuestProvider guestProvider = new GuestProvider();

        LocalDate seasonStart = LocalDate.of(2023, 8, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 8, 8);
        List<Guest> guests = guestProvider.generateGuests(100, seasonStart, seasonEnd);

        // Get guests for the day
        LocalDate dayToSimulate = LocalDate.of(2023, 8, 5);
        LocalDateTime breakfastTime = dayToSimulate.atStartOfDay().withHour(6);

        Set<Guest> guestsForToday = guestProvider.getGuestsForDay(guests, dayToSimulate);

        System.out.println("Guests for the day: ");
        for (Guest guest : guestsForToday) {
            System.out.println(guest);
        }

        System.out.println("Number of guests: " + guests.size());

        BreakfastGroups breakfastGroup = new BreakfastGroups();

        ArrayList<List<Guest>> groups = breakfastGroup.createGroups(guestsForToday);

        System.out.println("Guest groups: ");
        System.out.println(groups);

        // initialize meals
        Meal cereal = new Meal(MealType.CEREAL, 10, breakfastTime);
        Meal milk = new Meal(MealType.MILK, 10, breakfastTime);

        List<Meal> meals = new ArrayList<>(Arrays.asList(cereal, milk));
        Buffet buffet = new Buffet();

        buffet.setMeals(meals);

//        List<Meal> buffetMeals = buffet.getMeals();

        BuffetServiceImpl buffetService = new BuffetServiceImpl();

        List<Meal> mealsToRefill = new ArrayList<>();
        for (MealType mealType : MealType.values()) {
            mealsToRefill.add(new Meal(mealType, 2, null));
        }

        buffetService.cycleManager(buffet, mealsToRefill, groups);

    }
}