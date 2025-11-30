package ru.netology.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class InvalidVerificationPage {

    // Проверку видимости страницы добавили в конструктор,
    // чтобы данная проверка происходила автоматически каждый раз при загрузке страницы.
    public InvalidVerificationPage() {
        $("[data-test-id='code']").shouldBe(Condition.visible);
    }

    private final SelenideElement erorMassage = $("[data-test-id='error-notification']");

    public void invalidVerificationUser(DataHelper.VerificationСode code) {

        $("[data-test-id='code'] input").setValue(code.getCode());
        $("[data-test-id='action-verify']").click();

        }

    // Проверка видимости текста об ошибке в качестве аргумента передаем значение (String expectedText)
    // Означающее, что метод будет ожидать в качестве аргумента текст об ошибке
    public void erorMassage(String expectedText) {

        erorMassage.shouldHave(Condition.text(expectedText), Duration.ofSeconds(7)).shouldBe(Condition.visible);
    }
}
