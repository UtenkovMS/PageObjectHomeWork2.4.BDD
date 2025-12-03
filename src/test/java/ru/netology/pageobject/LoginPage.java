package ru.netology.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.getUserInfo;

public class LoginPage {

    private final SelenideElement erorMassage = $("[data-test-id='error-notification']");


    // Метод авторизация пользователя
    // Для того чтобы метод принимал данные о пользователе, укажем параметр (DataHelper.Authorization info)

    public VerificationPage authorizationUser(DataHelper.Authorization info) {

        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();

        return new VerificationPage();
    }

}
