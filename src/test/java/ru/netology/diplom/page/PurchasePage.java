package ru.netology.diplom.page;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PurchasePage {

    //Кнопки
    private SelenideElement buttonBuy = $$(".button").find(exactText("Купить"));
    private SelenideElement buttonBuyInCredit = $$(".button").find(exactText("Купить в кредит"));
    private SelenideElement buttonContinue = $$(".button").find(exactText("Продолжить"));

    //Поля
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cvcCodeField = $("[placeholder='999']");
    private SelenideElement ownerField = $$(".input__control").get(3);

    //Тексе title
    private SelenideElement title = $("title");

    //Уведомления
    private SelenideElement notificationInMonth = $(".input__sub");
    private SelenideElement errorNotification = $(".notification_status_error");
    //"Ошибка" + "Ошибка! Банк отказал в проведении операции."
    private SelenideElement successNotification = $(".notification_status_ok");
    //"Успешно" + "Операция одобрена Банком."


    public PurchasePage fillThePaymentForm (String cardNumber, String month, String year, String owner, String cvc) {
        buttonBuy.click();
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        cvcCodeField.setValue(cvc);
        ownerField.setValue(owner);
        buttonContinue.click();
        return this;
    }

    public PurchasePage fillTheCreditForm (String cardNumber, String month, String year, String owner, String cvc) {
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
