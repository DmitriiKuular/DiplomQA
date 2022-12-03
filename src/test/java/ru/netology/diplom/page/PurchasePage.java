package ru.netology.diplom.page;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PurchasePage {

    private SelenideElement buttonBuy = $$(".button").find(exactText("Купить"));
    private SelenideElement buttonBuyInCredit = $$(".button").find(exactText("Купить в кредит"));
    private SelenideElement buttonContinue = $$(".button").find(exactText("Продолжить"));

    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cvcCodeField = $("[placeholder='999']");
    private SelenideElement ownerField = $$(".input__control").get(3);

    public SelenideElement title = $("title");

    public SelenideElement notificationInMonth= $(".input__sub");

    public SelenideElement errorNotification = $(".notification_status_error");
    //"Ошибка" + "Ошибка! Банк отказал в проведении операции."
    public SelenideElement successNotification = $(".notification_status_ok");
    //"Успешно" + "Операция одобрена Банком."


    public PurchasePage fillTheForm (String cardNumber, String month, String year, String owner, String cvc) {
        buttonBuyInCredit.click();
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        cvcCodeField.setValue(cvc);
        ownerField.setValue(owner);
        buttonContinue.click();
        return this;
    }
}
