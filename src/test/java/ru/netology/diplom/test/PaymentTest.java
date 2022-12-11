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

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void start() {
        open("http://localhost:8080");
    }

    @AfterEach
    void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Should fill in the form successful first card data")
    void shouldFillTheFormSuccessfulFirstCard() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var correctCardData = DateDataGenerator.getLowerBoundDate();
        var successNotification = purchasePage.getSuccessNotification();
        purchasePage.fillThePaymentForm(firstCardData.getCardNumber(),
                correctCardData.getCardMonth(), correctCardData.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно " + "Операция одобрена Банком."));
        assertEquals("APPROVED", getCardStatusWhenPayment());
    }

    @Test
    @DisplayName("Should fill in the form successful second card data")
    void shouldFillTheFormSuccessfulSecondCard() {
        var purchasePage = new PurchasePage();
        var secondCardData = OtherDataGenerator.getSecondCardData("en");
        var correctCardData = DateDataGenerator.getLowerBoundDate();
        var successNotification = purchasePage.getSuccessNotification();
        purchasePage.fillThePaymentForm(secondCardData.getCardNumber(),
                correctCardData.getCardMonth(), correctCardData.getCardYear(),
                secondCardData.getCardOwner(), secondCardData.getCvcCode());
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно " + "Операция одобрена Банком."));
        assertEquals(getCardStatusWhenPayment(), "DECLINED");
    }

    @Test
    @DisplayName("Should fill in the upper bound in date input")
    void shouldFillUpperBoundDate() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var lowerBoundCardDate = DateDataGenerator.getUpperBoundDate();
        var successNotification = purchasePage.getSuccessNotification();
        purchasePage.fillThePaymentForm(firstCardData.getCardNumber(),
                lowerBoundCardDate.getCardMonth(), lowerBoundCardDate.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно " + "Операция одобрена Банком."));
        assertEquals("APPROVED", getCardStatusWhenPayment());
    }

    @Test
    @DisplayName("Should fill in the form card data with previous date")
    void shouldFillPreviousDateCard() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var previousCardDate = DateDataGenerator.getPreviousCardDate();
        var errorNotification = purchasePage.getNotificationInMonth();
        purchasePage.fillThePaymentForm(firstCardData.getCardNumber(),
                previousCardDate.getCardMonth(), previousCardDate.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("Should fill in the form card data with over period date")
    void shouldFillOverPeriodDateCard() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var overPeriodCardDate = DateDataGenerator.getOverCardDate();
        var errorNotification = purchasePage.getNotificationInMonth();
        purchasePage.fillThePaymentForm(firstCardData.getCardNumber(),
                overPeriodCardDate.getCardMonth(), overPeriodCardDate.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("Should show error notification if wrong format value in Month input")
    void shouldShowErrorNotificationIfWrongFormatValueMonth() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var currentDate = DateDataGenerator.getLowerBoundDate();
        var wrongDateValueFormat = DateDataGenerator.wrongValueInputDateFormat();
        var errorNotification = purchasePage.getNotificationInMonth();
        purchasePage.fillThePaymentForm(firstCardData.getCardNumber(),
                wrongDateValueFormat, currentDate.getCardYear(), firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test
    @DisplayName("Should show error notification if wrong format value in Year input")
    void shouldShowErrorNotificationIfWrongFormatValueYear() {
        var purchasePage = new PurchasePage();
        var firstCardData = OtherDataGenerator.getFirstCardData("en");
        var currentDate = DateDataGenerator.getLowerBoundDate();
        var wrongDateValueFormat = DateDataGenerator.wrongValueInputDateFormat();
        var errorNotification = purchasePage.getNotificationInMonth();
        purchasePage.fillThePaymentForm(firstCardData.getCardNumber(),
                currentDate.getCardMonth(), wrongDateValueFormat, firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test //Не забыть написать в title shouldBe(exactOwnText("Покупка туров"))
    @DisplayName("Should be right the title name")
    void shouldBeRightTitle() {
        PurchasePage purchase = new PurchasePage();
        var title = purchase.getTitle();
        title.shouldBe(exactOwnText("AQA: Заявка на карту"));
    }

//    @Test
//    void test() {
//        System.out.println(SQLHelper.getPaymentCardStatus() + "APPROVED  DECLINED");
//    }
}
