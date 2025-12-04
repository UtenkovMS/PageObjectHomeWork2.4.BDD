package ru.netology.pageobject;

import com.codeborne.selenide.Condition;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;


import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement element = $("[data-test-id='dashboard']");
    private final SelenideElement amountFild = $("[data-test-id='amount'] input");
    private final SelenideElement cardNumberFild = $("[data-test-id='from'] input");
    private final SelenideElement button = $("button[data-test-id='action-transfer']");
    private final SelenideElement erorMassage = $("[data-test-id='error-notification']");


    public TransferPage() {
        element.shouldBe(Condition.visible);
    }

    // Перевод денег c карты на карту, в параметры метода передается информация о карте и сумма перевода
    public DashBoardPage transferMoney(DataHelper.CardInfo cardInfo, int amount) {

        //вызваем метод описанный ниже и передаем в параметры информацию о карте и сумме перевода
        makeTransfer(cardInfo, amount);

        return new DashBoardPage();
    }

    private void makeTransfer(DataHelper.CardInfo cardInfo, int amount) {

        // Класс Integer. - класс, который предоставляет методы работы с целыми числами
        // в частности класс Integer. позволяет вызывать метод toString(), который преобразует числа в строки
        // в качестве аргумента в метод toString(amount) мы передаем значение amount.
        amountFild.setValue(Integer.toString(amount));
        cardNumberFild.setValue(cardInfo.getCardNumber());
        button.click();
    }

    // Проверка видимости текста об ошибке в качестве аргумента передаем значение (String expectedText)
    // Означающее, что метод будет ожидать в качестве аргумента текст об ошибке
    public void erorMassage(String expectedText) {

        erorMassage.shouldBe(Condition.visible, Duration.ofSeconds(7)).shouldHave(Condition.text(expectedText));
    }

}
