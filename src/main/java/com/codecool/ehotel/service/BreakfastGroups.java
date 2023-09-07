package com.codecool.ehotel.service;

import com.codecool.ehotel.model.Guest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class BreakfastGroups {
    private static final int NUMBER_OF_GROUPS = 8;

    public ArrayList<List<Guest>> createGroups(Set<Guest> guests) {
        List<Guest> guestList = new ArrayList<>(guests);
        int guestCount = guests.size();

        ArrayList<Integer> groupSplitIndexArr = createGroupSplitIndices(guestCount);

        System.out.println(groupSplitIndexArr);

        ArrayList<List<Guest>> listOfGroups = createListOfGroups(guestList, groupSplitIndexArr);

        logGroups(listOfGroups);
        return listOfGroups;
    }

    private ArrayList<Integer> createGroupSplitIndices(int guestCount) {
        ArrayList<Integer> groupSplitIndexArr = new ArrayList<>(NUMBER_OF_GROUPS);
        groupSplitIndexArr.add(0);
        groupSplitIndexArr.add(guestCount);
        for (int i = 0; i < NUMBER_OF_GROUPS - 1; i++) {
            int splitIndex = (int) Math.floor(Math.random() * guestCount);
            groupSplitIndexArr.add(splitIndex);
        }
        groupSplitIndexArr.sort(Comparator.comparing(Integer::valueOf));
        return groupSplitIndexArr;
    }

    private ArrayList<List<Guest>> createListOfGroups(List<Guest> guestList, ArrayList<Integer> groupSplitIndexArr) {
        ArrayList<List<Guest>> listOfGroups = new ArrayList<>(NUMBER_OF_GROUPS);
        for (int i = 0; i < NUMBER_OF_GROUPS; i++) {
            int subStartIndex = groupSplitIndexArr.get(i);
            int subEndIndex = groupSplitIndexArr.get(i + 1);
            listOfGroups.add(new ArrayList<>(guestList.subList(subStartIndex, subEndIndex)));
        }
        return listOfGroups;
    }

    private void logGroups(ArrayList<List<Guest>> listOfGroups) {
        for (int i = 0; i < NUMBER_OF_GROUPS; i++) {
            System.out.println("Group " + (i + 1) + ": " + listOfGroups.get(i));
        }
    }
}
