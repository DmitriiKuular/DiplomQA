package ru.netology.diplom.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@NoArgsConstructor
@Data

public class StartPage {

    //Кнопки
    private static SelenideElement buttonBuy = $$(".button").find(exactText("Купить"));
    private static SelenideElement buttonBuyInCredit = $$(".button").find(exactText("Купить в кредит"));

    //Текст title
    private SelenideElement title = $("title");

    public static PurchasePage getPaymentPage() {
        buttonBuy.click();
        return new PurchasePage();
    }

    public static PurchasePage getCreditPage() {
        buttonBuyInCredit.click();
        return new PurchasePage();
    }
}
