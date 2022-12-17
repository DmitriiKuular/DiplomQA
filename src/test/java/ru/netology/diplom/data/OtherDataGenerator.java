package ru.netology.diplom.data;

import com.github.javafaker.Faker;
import lombok.*;

import java.util.Locale;

@NoArgsConstructor
@Data

public class OtherDataGenerator {

    private static String invalidSymbols = "/.,|?$%^':!@#*()_+-dй №ё";
    private static String invalidSymbolsForOwnerField = "/3,|?$%^:!@#*()+№";
    @Value
    public static class DataCard {
        private String cardNumber;
        private String cardOwner;
        private String cvcCode;
    }

    public static String getInvalidSymbols() {
        return invalidSymbols;
    }

    public static String getInvalidSymbolsForOwnerField() {
        return invalidSymbolsForOwnerField;
    }

    public static String generateOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName().toUpperCase();
        return name;
    }

    public static String generateCodeCVC() {
        int a = 1000 + (int) (Math.random() * 1000);
        String f = String.valueOf(a);
        String cvcCode = f.substring(1);
        return cvcCode;
    }

    public static DataCard getFirstCardData(String locale) {
        return new DataCard("4444 4444 4444 4441", generateOwner(locale), generateCodeCVC());
    }

    public static DataCard getSecondCardData(String locale) {
        return new DataCard("4444 4444 4444 4442", generateOwner(locale), generateCodeCVC());
    }

    //Метод генерирует 15ти значный номер карты
    public static String generateFifteenValuesNumber() {
        long a = 1_000_000_000_000_000L + (long) (Math.random() * 1_000_000_000_000_000L);
        String f = String.valueOf(a);
        String cardNumberFifteenValues = f.substring(1);
        return cardNumberFifteenValues;
    }

    //Метод для ввода в поле "Номер карты" значений больше 16ти с использованием спец-символов
    public static String getRightNumberWithInvalidSymbols() {
        String firstHalfANumber = "1111 2222";
        String secondHalfANumber = "3333 4444";
        String additionalValues = "55 888";
        String result = firstHalfANumber + invalidSymbols + secondHalfANumber + additionalValues;
        return result;
    }

    //Метод генерирует 2х значный СVC код
    public static String generateWrongCVCCodeFormat() {
        int wrongCVCCode = (int) (Math.random() * 99);
        return String.valueOf(wrongCVCCode);
    }

    public static DataCard getWrongDataCard(String locale) {
        return new DataCard(generateFifteenValuesNumber(), generateOwner(locale), generateWrongCVCCodeFormat());
    }

    public static DataCard getRightDataCard(String locale) {
        return new DataCard(getRightNumberWithInvalidSymbols(), generateOwner(locale), generateCodeCVC());
    }
}
