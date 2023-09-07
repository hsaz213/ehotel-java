package com.codecool.ehotel.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Meal {
    private MealType mealType;
    private int amount;
    private LocalDateTime timeStamp;

    public Meal(MealType mealType, int amount, LocalDateTime timeStamp) {
        this.mealType = mealType;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = LocalDateTime.from(timeStamp);
    }

    public void increaseAmount(int number) {
        this.amount += number;
    }

    public void decreaseAmount(int number) {
        this.amount -= number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return amount == meal.amount && mealType == meal.mealType && Objects.equals(timeStamp, meal.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealType, amount, timeStamp);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealType=" + mealType +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
