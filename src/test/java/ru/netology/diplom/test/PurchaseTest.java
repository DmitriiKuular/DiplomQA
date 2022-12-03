package ru.netology.diplom.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.diplom.data.DateDataGenerator;
import ru.netology.diplom.data.OtherDataGenerator;
import ru.netology.diplom.page.PurchasePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PurchaseTest {

    @BeforeEach
    void start() {
        open("http://localhost:8080");
    }

    @Test
    @DisplayName("Should fill the form successful first card data")
    void shouldFillTheFormSuccessfulFirstCard() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var correctCardData = DateDataGenerator.getCorrectCardDate();
        purchasePage.fillTheForm(firstCardData.getCardNumber(),
                correctCardData.getCardMonth(), correctCardData.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        purchasePage.successNotification.shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(exactText("Успешно " + "Операция одобрена Банком."));
    }

    @Test
    @DisplayName("Should fill the form first card data with previous date")
    void shouldFillPreviousDateFirstCard() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var previousCardDate = DateDataGenerator.getPreviousCardDate();
        purchasePage.fillTheForm(firstCardData.getCardNumber(),
                previousCardDate.getCardMonth(), previousCardDate.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        purchasePage.notificationInMonth.shouldBe(visible).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("Should fill the form successful second card data")
    void shouldFillTheFormSuccessfulSecondCard() {
        var purchasePage = new PurchasePage();
        var secondCardData = OtherDataGenerator.getSecondCardData("en");
        var correctCardData = DateDataGenerator.getCorrectCardDate();
        purchasePage.fillTheForm(secondCardData.getCardNumber(),
                correctCardData.getCardMonth(), correctCardData.getCardYear(),
                secondCardData.getCardOwner(), secondCardData.getCvcCode());
        purchasePage.successNotification.shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(exactText("Успешно " + "Операция одобрена Банком."));
    }

    @Test //Не забыть написать в title shouldBe(exactOwnText("Покупка туров"))
    @DisplayName("Should be right the title name")
    void shouldBeRightTitle() {
        PurchasePage purchase = new PurchasePage();
        purchase.title.shouldBe(exactOwnText("AQA: Заявка на карту"));
    }


}
