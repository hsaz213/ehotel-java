package com.codecool.ehotel.service;

import com.codecool.ehotel.model.Guest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.lang.Math.floor;

public class BreakfastGroups {

    public ArrayList<List<Guest>> createGroups(Set<Guest> guests) {
        List<Guest> guestList = new ArrayList<>(guests);

        int guestCount = guests.size();

        ArrayList<Integer> groupSplitIndexArr = new ArrayList<>(8);
        int firstIndex = 0;
        int lastIndex = guests.size();
        groupSplitIndexArr.add(firstIndex);
        groupSplitIndexArr.add(lastIndex);
        for (int i = 0; i < 7; i++) {
            int splitIndex = (int) floor(Math.random() * guestCount);
            groupSplitIndexArr.add(splitIndex);
        }
        groupSplitIndexArr.sort(Comparator.comparing(Integer::valueOf));

        System.out.println(groupSplitIndexArr);

        ArrayList<List<Guest>> listOfGroups = new ArrayList<List<Guest>>(8);
        for (int i = 0; i < 8; i++) {
            int subStartIndex = groupSplitIndexArr.get(i);
            int subEndIndex = groupSplitIndexArr.get(i + 1);
            listOfGroups.add(guestList.subList(subStartIndex, subEndIndex));
        }

        for (int i = 0; i < 8; i++) {
            System.out.println("Group " + String.valueOf(i + 1) + " " + listOfGroups.get(i));
        }
        return listOfGroups;
    }
}