package ru.netology.diplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.data.DateGenerator;
import ru.netology.diplom.data.DataGenerator;
import ru.netology.diplom.pages.StartPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diplom.data.SQLHelper.*;
import static ru.netology.diplom.pages.PurchasePage.sendEmptyCreditForm;


public class CreditTest {
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

    @Test //Тест на отправку формы, заполненной данными первой карты, и получение статуса из БД
    @DisplayName("Should fill in the form successful first card data")
    void shouldFillTheFormSuccessfulFirstCard() {
        var creditPage = StartPage.getCreditPage();
        var firstCardData = DataGenerator.getFirstCardData("en");
        var correctCardData = DateGenerator.getLowerBoundDate();
        var successNotification = creditPage.getSuccessNotificationOfTransaction();
        creditPage.fillTheCreditForm(firstCardData.getCardNumber(),
                correctCardData.getCardMonth(), correctCardData.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно " + "Операция одобрена Банком."));
        assertEquals("APPROVED", getCardStatusWhenCredit());
    }

    @Test //Тест на отправку формы, заполненной данными второй карты, и получение статуса из БД
    @DisplayName("Should fill in the form successful second card data")
    void shouldFillTheFormSuccessfulSecondCard() {
        var creditPage = StartPage.getCreditPage();
        var secondCardData = DataGenerator.getSecondCardData("en");
        var correctCardData = DateGenerator.getLowerBoundDate();
        var successNotification = creditPage.getSuccessNotificationOfTransaction();
        creditPage.fillTheCreditForm(secondCardData.getCardNumber(),
                correctCardData.getCardMonth(), correctCardData.getCardYear(),
                secondCardData.getCardOwner(), secondCardData.getCvcCode());
        successNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Отказ " + "Операция отклонена Банком."));
        assertEquals("DECLINED", getCardStatusWhenCredit());
    }

    @Test //Тест на отправку пустой формы
    @DisplayName("Should show error notifications under all the fields if send empty form")
    void shouldShowErrorUnderAllFieldsIfFormIsEmpty() {
        var creditPage = StartPage.getCreditPage();
        var errorInCardNumber = creditPage.getErrorInCardNumberField();
        var errorInMonth = creditPage.getErrorInMonthField();
        var errorInYear = creditPage.getErrorInYearField();
        var errorInOwner = creditPage.getErrorInOwnerField();
        var errorInCVC = creditPage.getErrorInCVCCodeField();
        sendEmptyCreditForm();
        errorInCardNumber.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInMonth.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInYear.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInOwner.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInCVC.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test //Тест на валидацию поля "Номер карты". Поле заполнено номером из 15ти значений
    @DisplayName("Should show error notification if fill in 15th values in card number field")
    void shouldShowErrorIfFifteenValuesInCardNumber() {
        var creditPage = StartPage.getCreditPage();
        var rightCardData = DataGenerator.getRightDataCard("en");
        var wrongCardData = DataGenerator.getWrongDataCard("ru");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var errorInCardData = creditPage.getErrorInCardNumberField();
        creditPage.fillTheCreditForm(wrongCardData.getCardNumber(), cardPeriod.getCardMonth(),
                cardPeriod.getCardYear(), rightCardData.getCardOwner(), rightCardData.getCvcCode());
        errorInCardData.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    //Тест на валидацию поля "Номер карты". Поле заполняется валидными и невалидными данными, и значениями более 16ти
    @Test //Поле должно принимать только 16 валидных значений
    @DisplayName("Should fill in card number field with valid values (1111 2222 3333 4444)")
    void shouldFillCardNumberFieldWithValidValues() {
        var creditPage = StartPage.getCreditPage();
        var rightCardData = DataGenerator.getRightDataCard("en");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var errorNotification = creditPage.getErrorNotificationOfTransaction();
        var resultValueInCardNumber = creditPage.getCardNumberField();
        creditPage.fillTheCreditForm(rightCardData.getCardNumber(), cardPeriod.getCardMonth(),
                cardPeriod.getCardYear(), rightCardData.getCardOwner(), rightCardData.getCvcCode());
        assertEquals("1111 2222 3333 4444", resultValueInCardNumber.getValue());
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Ошибка " + "Ошибка! Банк отказал в проведении операции."));
    }

    @Test //Тест на ввод данных максимального срока действия карты(+ 5 лет), по принципу граничных значений
    @DisplayName("Should fill in the upper bound in date input")
    void shouldFillUpperBoundDate() {
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var lowerBoundCardDate = DateGenerator.getUpperBoundDate();
        var errorNotification = creditPage.getErrorNotificationOfTransaction();
        creditPage.fillTheCreditForm( correctCardData.getCardNumber(),
                lowerBoundCardDate.getCardMonth(), lowerBoundCardDate.getCardYear(),
                correctCardData.getCardOwner(),  correctCardData.getCvcCode());
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Ошибка " + "Ошибка! Банк отказал в проведении операции."));
    }

    @Test // Тест на ввод данных истекшего срока действия карты на месяц, по принципу граничных значений
    @DisplayName("Should fill in the form card data with previous month date")
    void shouldFillPreviousMonthDateCard() {
        var creditPage = StartPage.getCreditPage();
        var firstCardData = DataGenerator.getFirstCardData("en");
        var previousCardDate = DateGenerator.getPreviousMonthCardDate();
        var errorNotification = creditPage.getErrorInMonthField();
        creditPage.fillTheCreditForm(firstCardData.getCardNumber(),
                previousCardDate.getCardMonth(), previousCardDate.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Истёк срок действия карты"));
    }

    @Test // Тест на ввод предыдущего года от текущего в поле "Год"
    @DisplayName("Should fill in the form card data with previous year date")
    void shouldFillPreviousYearDateCard() {
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var previousCardDate = DateGenerator.getPreviousYearCardDate();
        var errorNotification = creditPage.getErrorInYearField();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(),
                previousCardDate.getCardMonth(), previousCardDate.getCardYear(),
                correctCardData.getCardOwner(), correctCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Истёк срок действия карты"));
    }

    @Test // Тест на ввод данных срока действия карты, превышающего максимальный, по принципу граничных значений
    @DisplayName("Should fill in the form card data with over period date")
    void shouldFillOverPeriodDateCard() {
        var creditPage = StartPage.getCreditPage();
        var firstCardData = DataGenerator.getFirstCardData("en");
        var overPeriodCardDate = DateGenerator.getOverCardDate();
        var errorNotification = creditPage.getErrorInYearField();
        creditPage.fillTheCreditForm(firstCardData.getCardNumber(),
                overPeriodCardDate.getCardMonth(), overPeriodCardDate.getCardYear(),
                firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Test //Тест на ввод значения "00" в поле "Месяц"
    @DisplayName("Should show error notification if fill in '00' in Month input")
    void shouldShowErrorIfNullNullValueInMonthInput(){
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var nextYearsDate = DateGenerator.getUpperBoundDate();
        var errorNotification = creditPage.getErrorInMonthField();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(), "00", nextYearsDate.getCardYear(),
                correctCardData.getCardOwner(), correctCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Test //Тест на ввод 13 месяца в поле "Месяц"
    @DisplayName("Should show error notification if fill in '13' in Month input")
    void shouldShowErrorIfWrongMonthValueInMonthInput(){
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var currentDate = DateGenerator.getLowerBoundDate();
        var errorNotification = creditPage.getErrorInMonthField();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(), "13", currentDate.getCardYear(),
                correctCardData.getCardOwner(), correctCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверно указан срок действия карты"));
    }

    @Test // Тест на ввод данных в поле "Месяц" неверного формата
    @DisplayName("Should show error notification if wrong format value in Month input")
    void shouldShowErrorNotificationIfWrongFormatValueMonth() {
        var creditPage = StartPage.getCreditPage();
        var firstCardData = DataGenerator.getFirstCardData("en");
        var currentDate = DateGenerator.getLowerBoundDate();
        var wrongDateValueFormat = DateGenerator.wrongValueInputDateFormat();
        var errorNotification = creditPage.getErrorInMonthField();
        creditPage.fillThePaymentForm(firstCardData.getCardNumber(),
                wrongDateValueFormat, currentDate.getCardYear(), firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test //Тест на ввод данных в поле "Год" неверного формата
    @DisplayName("Should show error notification if wrong format value in Year input")
    void shouldShowErrorNotificationIfWrongFormatValueYear() {
        var creditPage = StartPage.getCreditPage();
        var firstCardData = DataGenerator.getFirstCardData("en");
        var currentDate = DateGenerator.getLowerBoundDate();
        var wrongDateValueFormat = DateGenerator.wrongValueInputDateFormat();
        var errorNotification = creditPage.getErrorInYearField();
        creditPage.fillTheCreditForm(firstCardData.getCardNumber(),
                currentDate.getCardMonth(), wrongDateValueFormat, firstCardData.getCardOwner(), firstCardData.getCvcCode());
        errorNotification.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test //Ввод невалидных данных в поле "Владелец" на кириллице
    @DisplayName("Should show error notification if fill invalid cyrillic values in owner field")
    void shouldShowErrorIfInvalidCyrillicValuesInOwner() {
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var wrongCardData = DataGenerator.getWrongDataCard("ru");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var errorOwner = creditPage.getErrorInOwnerField();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(), cardPeriod.getCardMonth(),
                cardPeriod.getCardYear(), wrongCardData.getCardOwner(),
                correctCardData.getCvcCode());
        errorOwner.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test //Ввод невалидных данных в поле "Владелец", включая спецсимволы
    @DisplayName("Should show error notification if fill invalid symbols values in owner field")
    void shouldShowErrorIfInvalidValuesInOwner() {
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var invalidSymbols = DataGenerator.getInvalidSymbolsForOwnerField();
        var errorOwner = creditPage.getErrorInOwnerField();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(), cardPeriod.getCardMonth(),
                cardPeriod.getCardYear(), correctCardData.getCardOwner() + invalidSymbols,
                correctCardData.getCvcCode());
        errorOwner.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test //Тест на ввод данных в поле "CVC/CVV" неверного формата
    @DisplayName("Should show error notification if fill wrong format values in CVC code field")
    void shouldShowErrorIfWrongFormatInCVCCode() {
        var creditPage = StartPage.getCreditPage();
        var rightCardData = DataGenerator.getRightDataCard("en");
        var wrongCardData = DataGenerator.getWrongDataCard("ru");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var errorInCVCCode = creditPage.getErrorInCVCCodeField();
        creditPage.fillTheCreditForm(rightCardData.getCardNumber(), cardPeriod.getCardMonth(),
                cardPeriod.getCardYear(), rightCardData.getCardOwner(), wrongCardData.getCvcCode());
        errorInCVCCode.shouldBe(visible).shouldHave(exactText("Неверный формат"));
    }

    @Test //Тест на заполнение невалидными данными полей 'Месяц', 'Год' и 'CVC/CVV'
    @DisplayName("Should not to fill fields 'Month', 'Year' and 'CVC/CVV' with invalid values")
    void shouldNotToFillFieldsWithInvalidValues() {
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var invalidSymbols = DataGenerator.getInvalidSymbols();
        var errorNotification = creditPage.getErrorNotificationOfTransaction();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(), invalidSymbols + cardPeriod.getCardMonth(),
                invalidSymbols + cardPeriod.getCardYear(), correctCardData.getCardOwner(),
                invalidSymbols + correctCardData.getCvcCode());
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Ошибка " + "Ошибка! Банк отказал в проведении операции."));
    }

    @Test //Тест на заполнение полей 'Месяц', 'Год' и 'CVC/CVV' данными, превышающими допустимое количество
    @DisplayName("Should not to fill fields 'Month', 'Year' and 'CVC/CVV' with values more then enough")
    void shouldNotToFillFieldsWithValuesMoreThenEnough() {
        var creditPage = StartPage.getCreditPage();
        var correctCardData = DataGenerator.getRightDataCard("en");
        var cardPeriod = DateGenerator.getLowerBoundDate();
        var errorNotification = creditPage.getErrorNotificationOfTransaction();
        creditPage.fillTheCreditForm(correctCardData.getCardNumber(), cardPeriod.getCardMonth() + "1",
                cardPeriod.getCardYear() + "1", correctCardData.getCardOwner(),
                correctCardData.getCvcCode() + "1");
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Ошибка " + "Ошибка! Банк отказал в проведении операции."));
    }
}