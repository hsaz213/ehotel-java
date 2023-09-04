package com.codecool.ehotel.service.logger;

import java.time.LocalDate;

public class ConsoleLogger implements Logger{

    LocalDate currentDate = LocalDate.now();
    @Override
    public void logInfo(String message) {
        System.out.println("[INFO] : " + currentDate + message);
    }

    @Override
    public void logError(String message) {
        System.out.println("[ERROR] : " + currentDate + message);

    }
}
