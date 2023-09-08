package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Meal;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.List;

public interface BuffetService {
//    Buffet refill(Buffet buffet, List<Meal> meals);

    Buffet refill(Buffet buffet, List<Meal> meals, LocalDateTime currentCycleStart);

    boolean consumeFreshest(Buffet buffet, MealType mealType);

    int collectWaste(Buffet buffet, LocalDateTime currentCycleEnd);
}