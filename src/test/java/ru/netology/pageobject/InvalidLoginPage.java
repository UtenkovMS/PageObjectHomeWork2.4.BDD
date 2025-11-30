package ru.netology.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class InvalidLoginPage {

        private final SelenideElement erorMassage = $("[data-test-id='error-notification']");

        // Реализуем доменный метод
        // authorizationInvalidUser - авторизация невалидного пользователя
        // Для того чтобы метод принимал данные о пользователе, укажем параметр (DataHelper.Authorization info)

        public void authorizationInvalidUser(DataHelper.Authorization info) {

            $("[data-test-id='login'] input").setValue(info.getLogin());
            $("[data-test-id='password'] input").setValue(info.getPassword());
            $("[data-test-id='action-login']").click();

        }

        // Проверка видимости текста об ошибке в качестве аргумента передаем значение (String expectedText)
        // Означающее, что метод будет ожидать в качестве аргумента текст об ошибке
        public void erorMassage(String expectedText) {

            erorMassage.shouldHave(Condition.text(expectedText), Duration.ofSeconds(7)).shouldBe(Condition.visible);
        }

    }
