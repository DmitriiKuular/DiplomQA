package ru.netology.diplom.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class OtherDataGenerator {

    private OtherDataGenerator() {}

    @Value
    public static class DataCard {
        private String cardNumber;
        private String cardOwner;
        private String cvcCode;
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

}
