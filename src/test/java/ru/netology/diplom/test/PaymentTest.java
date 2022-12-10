package ru.netology.diplom.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.data.*;
import ru.netology.diplom.page.PurchasePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.diplom.data.SQLHelper.*;

public class PaymentTest {

//    @BeforeAll
//    static void setUpAll() {
//        SelenideLogger.addListener("allure", new AllureSelenide());
//    }
//
//    @AfterAll
//    static void tearDownAll() {
//        SelenideLogger.removeListener("allure");
//    }

    @BeforeEach
    void start() {
        open("http://localhost:8080");
    }

//    @AfterEach
//    void teardown() {
//        cleanDatabase();
//    }

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
        assertEquals("APPROVED", SQLHelper.getPaymentCardStatus());
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
        assertEquals(getPaymentCardStatus(), "DECLINED");
    }

    @Test //Не забыть написать в title shouldBe(exactOwnText("Покупка туров"))
    @DisplayName("Should be right the title name")
    void shouldBeRightTitle() {
        PurchasePage purchase = new PurchasePage();
        purchase.title.shouldBe(exactOwnText("AQA: Заявка на карту"));
    }

//    @Test
//    void test() {
//        System.out.println(SQLHelper.getPaymentCardStatus() + "APPROVED  DECLINED");
//    }
}
