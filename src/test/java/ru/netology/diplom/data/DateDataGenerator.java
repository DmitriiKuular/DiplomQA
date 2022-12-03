package ru.netology.diplom.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateDataGenerator {

    private DateDataGenerator() {
    }

    private static LocalDate date = LocalDate.now();

    //Возвращает валидную дату (рандом от текущей до ровно 5 лет от текущей)
    //От параметра season завист то, что мы возвращаем (либо месяц, либо год)
    public static String returnCorrectDate(String season) {
        int[] correctCardPeriodYear = new int[]{0, 1, 2, 3, 4, 5};
        int n = (int)Math.floor(Math.random() * correctCardPeriodYear.length);
        int correctYear = correctCardPeriodYear[n];

        String currentDate = date.plusYears(correctYear).format(DateTimeFormatter.ofPattern("MM.yy"));
        String month = currentDate.substring(0, 2);
        String year = currentDate.substring(3);
        if (season.equals("month")) {
            return month;
        }
        if (season.equals("year")) {
            return year;
        }
        return null;
    }

    //Генерирует дату на месяц позже от текущей
    public static String generatePreviousMonthDate(String season) {
        String previousMonth = date.minusMonths(1).format(DateTimeFormatter.ofPattern("MM.yy"));
        String month = previousMonth.substring(0, 2);
        String year = previousMonth.substring(3);
        if (season.equals("month")) {
            return month;
        }
        if (season.equals("year")) {
            return year;
        }
        return null;
    }

    //Генерирует дату на пять лет и один месяц болще текущей.
    //Для негативного сценария (бановская карта выдается максимум на  лет)
    public static String generateOverCardPeriodYear(String season) {
        String overCardPeriodYear = date.plusYears(5).plusMonths(1)
                .format(DateTimeFormatter.ofPattern("MM.yy"));
        String month = overCardPeriodYear.substring(0, 2);
        String year = overCardPeriodYear.substring(3);
        if (season.equals("month")) {
            return month;
        }
        if (season.equals("year")) {
            return year;
        }
        return null;
    }

    public static String generateOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static DateDataGenerator.CardDate getCorrectCardDate() {
        return new DateDataGenerator.CardDate(returnCorrectDate("month"), returnCorrectDate("year"));
    }

    public static DateDataGenerator.CardDate getPreviousCardDate() {
        return new DateDataGenerator.CardDate(generatePreviousMonthDate("month"), generatePreviousMonthDate("year"));
    }

    public static DateDataGenerator.CardDate getOverCardDate() {
        return new DateDataGenerator.CardDate(generateOverCardPeriodYear("month"), generateOverCardPeriodYear("year"));
    }

    @Value
    public static class CardDate {
        private String cardMonth;
        private String cardYear;
    }
}
