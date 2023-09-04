package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GuestProvider implements GuestService{

    private final List<String> firstNames = List.of(    "Jennifer",
            "Matthew",
            "Emily",
            "Michael",
            "Sarah",
            "David",
            "Jessica",
            "Daniel",
            "Linda",
            "James",
            "Elizabeth",
            "William",
            "Amanda",
            "John",
            "Olivia",
            "Richard",
            "Sophia",
            "Robert",
            "Mia",
            "Thomas");

    private final List<String> lastNames = List.of(
            "Smith",
            "Johnson",
            "Brown",
            "Davis",
            "Jones",
            "Miller",
            "Wilson",
            "Moore",
            "Taylor",
            "Anderson",
            "Jackson",
            "Harris",
            "Martin",
            "Thompson",
            "White",
            "Hall",
            "Thomas",
            "Robinson",
            "Lewis",
            "Walker"
    );

    private String getRandomNames() {
        Random random = new Random();
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        return firstName + " " + lastName;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        return null;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        Set<Guest> guestsOnTheDate = new HashSet<>();
        for (Guest guest : guests) {
            if (!date.isAfter(guest.checkOut()) && !date.isBefore(guest.checkIn())) {
                guestsOnTheDate.add(guest);
            }
        }
        return guestsOnTheDate;
    }

    public List<Guest> generateGuests(int numberOfGuests, LocalDate seasonStart, LocalDate seasonEnd) {
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            guests.add(generateRandomGuest(seasonStart, seasonEnd));
        }
        return guests;
    }

    public LocalDate randomDateInSeason(LocalDate seasonStart, LocalDate seasonEnd) {
        long startDay = seasonStart.toEpochDay();
        long endDay = seasonEnd.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startDay, endDay);
        return LocalDate.ofEpochDay(randomDay);
    }

}
