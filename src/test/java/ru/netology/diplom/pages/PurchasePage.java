package ru.netology.diplom.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

@NoArgsConstructor
@Data

public class PurchasePage {

    //Кнопки
    private static SelenideElement buttonContinue = $$(".button").find(exactText("Продолжить"));

    //Поля
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cvcCodeField = $("[placeholder='999']");
    private SelenideElement ownerField = $(byXpath("//span[text()='Владелец']/parent::span//input[@class='input__control']"));

    //Уведомления полей
    private SelenideElement errorInCardNumberField = $(byXpath
            ("//input[@placeholder='0000 0000 0000 0000']/parent::span/parent::span//span[@class='input__sub']"));
    private SelenideElement errorInMonthField = $(byXpath
            ("//input[@placeholder='08']/parent::span/parent::span//span[@class='input__sub']"));
    private SelenideElement errorInYearField = $(byXpath
            ("//input[@placeholder='22']/parent::span/parent::span//span[@class='input__sub']"));
    private SelenideElement errorInOwnerField = $(byXpath("//span[text()='Владелец']/parent::span//span[@class='input__sub']"));
    private SelenideElement errorInCVCCodeField = $(byXpath
            ("//input[@placeholder='999']/parent::span/parent::span//span[@class='input__sub']"));

    //Уведомления приложения
    private SelenideElement errorNotificationOfTransaction = $(".notification_status_error");
    //"Ошибка " + "Ошибка! Банк отказал в проведении операции."
    private SelenideElement successNotificationOfTransaction = $(".notification_status_ok");
    //"Успешно " + "Операция одобрена Банком."


    public PurchasePage fillThePaymentForm(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        cvcCodeField.setValue(cvc);
        ownerField.setValue(owner);
        buttonContinue.click();
        return this;
    }

    public PurchasePage fillTheCreditForm(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        cvcCodeField.setValue(cvc);
        ownerField.setValue(owner);
        buttonContinue.click();
        return this;
    }

    public static void sendEmptyPaymentForm() {
        buttonContinue.click();
    }

    public static void sendEmptyCreditForm() {
        buttonContinue.click();
    }
}