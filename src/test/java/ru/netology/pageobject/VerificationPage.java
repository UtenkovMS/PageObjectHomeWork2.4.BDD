package ru.netology.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    // Проверку видимости страницы добавили в конструктор,
    // чтобы данная проверка происходила автоматически каждый раз при загрузке страницы.
    public VerificationPage() {
        $("[data-test-id='code']").shouldBe(Condition.visible);
    }

    public DashBoardPage verificationUser(DataHelper.VerificationСode code) {

        $("[data-test-id='code'] input").setValue(code.getCode());
        $("[data-test-id='action-verify']").click();

        return new DashBoardPage();

    }
}
