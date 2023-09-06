package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.temporal.ChronoUnit.DAYS;

public class GuestProvider implements GuestService {

    private final List<String> firstNames = List.of("Jennifer", "Matthew", "Emily", "Michael", "Sarah", "David", "Jessica", "Daniel", "Linda", "James", "Elizabeth", "William", "Amanda", "John", "Olivia", "Richard", "Sophia", "Robert", "Mia", "Thomas");

    private final List<String> lastNames = List.of("Smith", "Johnson", "Brown", "Davis", "Jones", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Jackson", "Harris", "Martin", "Thompson", "White", "Hall", "Thomas", "Robinson", "Lewis", "Walker");

    private String getRandomNames() {
        Random random = new Random();
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        return firstName + " " + lastName;
    }

    private int getStayLengthInDays(LocalDate seasonStart, LocalDate seasonEnd) {
        int seasonDuration = (int) (seasonEnd.toEpochDay() - seasonStart.toEpochDay());

        Random random = new Random();
        int min = 1;
        int max = 7;
        if (seasonDuration < 7) {
            max = seasonDuration;
        }
        return random.nextInt(max + min) + min;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        long daysBetweenSeason = DAYS.between(seasonStart, seasonEnd);
        LocalDate randomCheckOutDate = randomDateInSeason(seasonStart.plusDays(getStayLengthInDays(seasonStart, seasonEnd)), seasonEnd);
        LocalDate randomCheckInDate = randomCheckOutDate.minusDays(getStayLengthInDays(seasonStart, seasonEnd));
        return new Guest(getRandomNames(), GuestType.randomGuestType(), randomCheckInDate, randomCheckOutDate);
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

        // Ensure startDay is always less than endDay
        if (startDay >= endDay) {
            long temp = startDay;
            startDay = endDay - 1; // swap and adjust
            endDay = temp + 1;
        }

        long randomDay = ThreadLocalRandom.current().nextLong(startDay, endDay + 1);
        return LocalDate.ofEpochDay(randomDay);
    }

    public ArrayList<Guest> shuffleGuestList(Set<Guest> guestList) {
        ArrayList<Guest> guestListArr = new ArrayList<>(guestList.size());
        guestListArr.addAll(guestList);
        Collections.shuffle(guestListArr);
        return guestListArr;
    }

}
