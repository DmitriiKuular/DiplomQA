package ru.netology.diplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.pages.StartPage;

import static com.codeborne.selenide.Condition.exactOwnText;
import static com.codeborne.selenide.Selenide.open;

public class StartPageTest {

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

    @Test //Тест на отображение текста в заголовке вкладки стартовой страницы
    @DisplayName("Should be right the title name")
    void shouldBeRightTitle() {
        var startPage = new StartPage();
        var title = startPage.getTitle();
        title.shouldBe(exactOwnText("AQA: Покупка туров"));
    }
}
