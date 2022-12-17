package ru.netology.diplom.data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateDataGenerator {

    private DateDataGenerator() {
    }

    private static LocalDate date = LocalDate.now();

    //Генерирует текущую дату для проверки нижней границы срока действия карты
    private static String generateLowerBoundCardPeriodYear(String season) {
        String currentDate = date.format(DateTimeFormatter.ofPattern("MM.yy"));
        String result = cutNecessaryDatePeriod(currentDate, season);
        return result;
    }


    //Генерирует дату ровно на пять лет больше текущей.
    //Для проверки верхней границы срока действия карты (банковская карта выдается максимум на 5 лет)
    private static String generateUpperBoundCardPeriod(String season) {
        String upperBoundDate = date.plusYears(5).format(DateTimeFormatter.ofPattern("MM.yy"));
        String result = cutNecessaryDatePeriod(upperBoundDate, season);
        return result;
    }

    //Генерирует предыдущий месяц от текущего (негативный сценарий)
    private static String generatePreviousMonthDate(String season) {
        String previousMonth = date.minusMonths(1).format(DateTimeFormatter.ofPattern("MM.yy"));
        String result = cutNecessaryDatePeriod(previousMonth, season);
        return result;
    }

    //Генерирует дату с предыдущим годом от текущего
    private static String generatePreviousYearDate(String season) {
        String previousYear = date.minusYears(1).format(DateTimeFormatter.ofPattern("MM.yy"));
        String result = cutNecessaryDatePeriod(previousYear, season);
        return result;
    }

    //Генерирует дату на пять лет и один месяц больше текущей.
    //Для негативного сценария (банковская карта выдается максимум на 5 лет)
    private static String generateOverCardPeriodYear(String season) {
        String overCardPeriodYear = date.plusYears(5).plusMonths(1)
                .format(DateTimeFormatter.ofPattern("MM.yy"));
        String result = cutNecessaryDatePeriod(overCardPeriodYear, season);
        return result;
    }

    //Метод помогает возвращать либо месяц, либо год от сгенерированной даты в зависимости от запроса
    //От параметра season зависит то, что мы возвращаем (либо месяц, либо год)
    private static String cutNecessaryDatePeriod(String cutDate, String season) {
        String month = cutDate.substring(0, 2);
        String year = cutDate.substring(3);
        if (season.equals("month")) {
            return month;
        }
        if (season.equals("year")) {
            return year;
        }
        return null;
    }

    public static DateDataGenerator.CardDate getLowerBoundDate() {
        return new DateDataGenerator.CardDate(generateLowerBoundCardPeriodYear("month"),
                generateLowerBoundCardPeriodYear("year"));
    }

    public static DateDataGenerator.CardDate getUpperBoundDate() {
        return new DateDataGenerator.CardDate(generateUpperBoundCardPeriod("month"),
                generateUpperBoundCardPeriod("year"));
    }

    public static DateDataGenerator.CardDate getPreviousMonthCardDate() {
        return new DateDataGenerator.CardDate(generatePreviousMonthDate("month"),
                generatePreviousMonthDate("year"));
    }

    public static DateDataGenerator.CardDate getPreviousYearCardDate() {
        return new DateDataGenerator.CardDate(generatePreviousYearDate("month"),
                generatePreviousYearDate("year"));
    }

    public static DateDataGenerator.CardDate getOverCardDate() {
        return new DateDataGenerator.CardDate(generateOverCardPeriodYear("month"),
                generateOverCardPeriodYear("year"));
    }

    @Value
    public static class CardDate {
        private String cardMonth;
        private String cardYear;
    }

    public static String wrongValueInputDateFormat() {
        int[] correctCardPeriodYear = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int n = (int)Math.floor(Math.random() * correctCardPeriodYear.length);
        int wrongFormatValue = correctCardPeriodYear[n];
        return String.valueOf(wrongFormatValue);
    }
}