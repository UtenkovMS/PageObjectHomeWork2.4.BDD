package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pageobject.DashBoardPage;
import ru.netology.pageobject.InvalidLoginPage;
import ru.netology.pageobject.InvalidVerificationPage;
import ru.netology.pageobject.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class TestingMoneyTransfer {

    // Объявляем переменные на уровне класса, чтобы они были доступны разных тестах из одно места
    DashBoardPage dashBoardPage;
    DataHelper.CardInfo cardFirst;
    DataHelper.CardInfo cardTwo;
    int currentBalanceFirstCard;
    int currentBalanceTwoCard;
    int amountCard;

    // @BeforeEach - это аннотация в JUnit, означающая, что данный метод выполняется перед каждым тестом.
    @BeforeEach
    void openPage() {

        // Открытие стартовой страницы приложения
        open("http://localhost:9999");
    }

    @Test
    // Должен авторизировать клиента и выполнить перевод денег
    @DisplayName("Should authorize user and transfer money")
    void shouldAuthorizeUserAndTransferMoney() {

        // Получаем данные о пользователе и присваиваем их переменной info
        var info = DataHelper.getUserInfo();
        // Запрашиваем код пользователя с данными присвоенными переменной info
        var code = DataHelper.getCode(info);

        // Авторизация пользователя
        // Обращение к странице авторизации пользователя через переменную loginPage
        var loginPage = new LoginPage();

        // Через переменную loginPage вызываем метод авторизации пользователя .authorizationUser(info)
        // В который передаем данные о пользователе через переменную (info)
        // Авторизированной странице присваиваем переменную verificationPage
        var verificationPage = loginPage.authorizationUser(info);

        // Через переменную verificationPage вызываем метод verificationUser(code)
        // И передаем в качестве параметра код авторизации
        // Верифицированной странице присваиваем переменную dashBoardPage
        dashBoardPage = verificationPage.verificationUser(code);

        // Данные о карте
        cardFirst = DataHelper.getFirstCard();
        cardTwo = DataHelper.getTwoCard();

        // Метод генеарции суммы начисления
        amountCard = DataHelper.generateAmount();

        // Проверка текущего баланса карты
        currentBalanceTwoCard = dashBoardPage.getCardBalance(cardTwo);
        currentBalanceFirstCard = dashBoardPage.getCardBalance(cardFirst);

        //Перевод средств с карты на карту
        // Условие. Если баланс второй карты меньше суммы перевода
        if (currentBalanceTwoCard < amountCard) {

            //Выбор карты
            var selectCard = dashBoardPage.selectCard(cardFirst);

            // То появляется информационное сообщение о том что, сумма перевода не может превышать баланс карты.
            selectCard.erorMassage("Сумма перевода не может превышать баланс карты");

            //Иначе, если условие не выполняется осуществляется перевод средств
        } else {

            // Выбор карты
            var selectCard = dashBoardPage.selectCard(cardFirst);
            // Перевод средств
            var transferPage = selectCard.transferMoney(cardTwo, amountCard);

        }

        //Обновление баланса карт.
        dashBoardPage.reloadBalance();

        // Проверка соответствия ожидаемых и фактических балансов карт
        dashBoardPage.checkingbalance(cardFirst, currentBalanceFirstCard + amountCard);
        dashBoardPage.checkingbalance(cardTwo, currentBalanceTwoCard - amountCard);

        System.out.println("Сумма перевода: " + amountCard);
        System.out.println("Баланс карты 5559 0000 0000 0001: " + (currentBalanceFirstCard + amountCard));
        System.out.println("Баланс карты 5559 0000 0000 0002: " + (currentBalanceTwoCard - amountCard));

    }

    @Test
    // Должен авторизировать невалидного пользователя
    @DisplayName("Should authorize invalid user")
    void shouldAuthorizeInvalidUser() {

        // Получаем данные о пользователе и присваиваем их переменной info
        var info = DataHelper.getUserTwoInfo();

        // Авторизация пользователя
        // Обращение к странице авторизации пользователя через переменную loginPage
        var invalidLoginPage = new InvalidLoginPage();

        // Через переменную loginPage вызываем метод авторизации пользователя .authorizationUser(info)
        // В который передаем данные о пользователе через переменную (info)
        // Авторизированной странице присваиваем переменную verificationPage
        invalidLoginPage.authorizationInvalidUser(info);
        invalidLoginPage.erorMassage("Ошибка! Неверно указан логин или пароль");

    }

    @Test
    // Должен авторизировать валидного пользователя с невалидным кодом авторизации
    @DisplayName("Should authorize valid user with an invalid authorization code ")
    void shouldAuthorizeInvalidinvalidAuthorizationCode() {

        // Получаем данные о пользователе и присваиваем их переменной info
        var info = DataHelper.getUserInfo();
        // Получаем неверный код из метода getInvalidCode(info)
        var code = DataHelper.getInvalidCode(info);

        // Авторизация пользователя
        // Обращение к странице авторизации пользователя через переменную loginPage
        var loginPage = new LoginPage();

        // Через переменную loginPage вызываем метод авторизации пользователя .authorizationUser(info)
        // В который передаем данные о пользователе через переменную (info)
        // Авторизированной странице присваиваем переменную verificationPage

        loginPage.authorizationUser(info);

        // Через переменную verificationPage вызываем метод verificationUser(code)
        // И передаем в качестве параметра код авторизации
        // Верифицированной странице присваиваем переменную dashBoardPage

        var invalidVerificationPage = new InvalidVerificationPage();

        invalidVerificationPage.invalidVerificationUser(code);

        invalidVerificationPage.erorMassage("Ошибка! Неверно указан код! Попробуйте ещё раз.");

    }
}
