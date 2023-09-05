package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.guest.GuestProvider;
import com.codecool.ehotel.service.BreakfastGroups;

import java.time.LocalDate;
import java.util.*;

import static java.lang.Math.floor;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services


        // Generate guests for the season
        GuestProvider guestProvider = new GuestProvider();

        LocalDate seasonStart = LocalDate.of(2023, 6, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 9, 1);
        List<Guest> guests = guestProvider.generateGuests(10000, seasonStart, seasonEnd);

        // Get guests for today
        LocalDate current = LocalDate.now();
        Set<Guest> guestsForToday = guestProvider.getGuestsForDay(guests, current);

        // Print guests for today
        System.out.println("Guests for today are: ");
        for (Guest guest : guestsForToday) {
            System.out.println(guest);
        }

        BreakfastGroups breakfastGroup = new BreakfastGroups();

        ArrayList<List<Guest>> groups = breakfastGroup.createGroups(guestsForToday);

        System.out.println(groups);


    }
}