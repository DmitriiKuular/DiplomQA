package ru.netology.diplom.data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateGenerator {

    private DateGenerator() {
    }

    private static LocalDate date = LocalDate.now();

    //Генерирует текущую дату для проверки нижней границы срока действия карты
    private static String generateLowerBoundCardPeriodYear(String calendarType) {
        var currentDate = date.format(DateTimeFormatter.ofPattern("MM.yy"));
        return cutNecessaryDatePeriod(currentDate, calendarType);
    }


    //Генерирует дату ровно на пять лет больше текущей.
    //Для проверки верхней границы срока действия карты (банковская карта выдается максимум на 5 лет)
    private static String generateUpperBoundCardPeriod(String calendarType) {
        var upperBoundDate = date.plusYears(5).format(DateTimeFormatter.ofPattern("MM.yy"));
        return cutNecessaryDatePeriod(upperBoundDate, calendarType);
    }

    //Генерирует предыдущий месяц от текущего (негативный сценарий)
    private static String generatePreviousMonthDate(String calendarType) {
        var previousMonth = date.minusMonths(1).format(DateTimeFormatter.ofPattern("MM.yy"));
        return cutNecessaryDatePeriod(previousMonth, calendarType);
    }

    //Генерирует дату с предыдущим годом от текущего
    private static String generatePreviousYearDate(String calendarType) {
        var previousYear = date.minusYears(1).format(DateTimeFormatter.ofPattern("MM.yy"));
        return cutNecessaryDatePeriod(previousYear, calendarType);
    }

    //Генерирует дату на пять лет и один месяц больше текущей.
    //Для негативного сценария (банковская карта выдается максимум на 5 лет)
    private static String generateOverCardPeriodYear(String calendarType) {
        var overCardPeriodYear = date.plusYears(5).plusMonths(1)
                .format(DateTimeFormatter.ofPattern("MM.yy"));
        return cutNecessaryDatePeriod(overCardPeriodYear, calendarType);
    }

    //Метод помогает возвращать либо месяц, либо год от сгенерированной даты в зависимости от запроса
    //От параметра calendarType(варианты для calendarType - month, year) зависит то, что мы возвращаем (либо месяц, либо год)
    private static String cutNecessaryDatePeriod(String cutDate, String calendarType) {
        var month = cutDate.substring(0, 2);
        var year = cutDate.substring(3);
        if (calendarType.equals("month")) {
            return month;
        }
        if (calendarType.equals("year")) {
            return year;
        }
        return null;
    }

    public static DateGenerator.CardDate getLowerBoundDate() {
        return new DateGenerator.CardDate(generateLowerBoundCardPeriodYear("month"),
                generateLowerBoundCardPeriodYear("year"));
    }

    public static DateGenerator.CardDate getUpperBoundDate() {
        return new DateGenerator.CardDate(generateUpperBoundCardPeriod("month"),
                generateUpperBoundCardPeriod("year"));
    }

    public static DateGenerator.CardDate getPreviousMonthCardDate() {
        return new DateGenerator.CardDate(generatePreviousMonthDate("month"),
                generatePreviousMonthDate("year"));
    }

    public static DateGenerator.CardDate getPreviousYearCardDate() {
        return new DateGenerator.CardDate(generatePreviousYearDate("month"),
                generatePreviousYearDate("year"));
    }

    public static DateGenerator.CardDate getOverCardDate() {
        return new DateGenerator.CardDate(generateOverCardPeriodYear("month"),
                generateOverCardPeriodYear("year"));
    }

    @Value
    public static class CardDate {
        private String cardMonth;
        private String cardYear;
    }

    public static String wrongValueInputDateFormat() {
        int[] correctCardPeriodYear = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        var random = (int)Math.floor(Math.random() * correctCardPeriodYear.length);
        var wrongFormatValue = correctCardPeriodYear[random];
        return String.valueOf(wrongFormatValue);
    }
}