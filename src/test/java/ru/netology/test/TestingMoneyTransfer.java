package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pageobject.DashBoardPage;
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

        // Получаем данные о пользователе и присваиваем их переменной userInfo
        var userInfo = DataHelper.getUserInfo();
        // Запрашиваем код верификации для пользователя с переменной userInfo
        var code = DataHelper.getCode(userInfo);

        // Авторизация пользователя
        // Обращаемся к странице авторизации пользователя через переменную loginPage
        var loginPage = new LoginPage();

        // Через переменную loginPage вызываем метод авторизации пользователя .authorizationUser(userInfo)
        // В который передаем данные о пользователе через переменную (userInfo)
        // Авторизированной странице присваиваем переменную verificationPage
        var verificationPage = loginPage.authorizationUser(userInfo);

        // Через переменную verificationPage вызываем метод verificationUser(code)
        // В который передаем в качестве параметра код авторизации (code)
        // Верифицированной странице присваиваем переменную dashBoardPage
        dashBoardPage = verificationPage.verificationUser(code);

        // Данные о карте
        cardFirst = DataHelper.getFirstCard();
        cardTwo = DataHelper.getTwoCard();

        // Получение балансов карт

        //Баланс первой карты "5559 0000 0000 0001"
        currentBalanceFirstCard = dashBoardPage.getCardBalance(cardFirst);

        //Баланс второй карты "5559 0000 0000 0002"
        currentBalanceTwoCard = dashBoardPage.getCardBalance(cardTwo);

    }

    @Test
    // Должен авторизировать клиента и выполнить перевод валидной суммы
    @DisplayName("Should authorize user and transfer valid amount money")
    void shouldAuthorizeUserAndTransferValidAmountMoney() {


        // Метод генеарции валидной суммы начисления
        amountCard = DataHelper.generateValidAmmount(currentBalanceTwoCard);

        //Выбор карты
        var selectCard = dashBoardPage.selectCard(cardFirst);

        // Перевод средств
        var transferPage = selectCard.transferMoney(cardTwo, amountCard);

        //Обновление баланса карт
        dashBoardPage.reloadBalance();

        // Проверка соответствия ожидаемых и фактических балансов карт
        dashBoardPage.checkingbalance(cardFirst, currentBalanceFirstCard + amountCard);
        dashBoardPage.checkingbalance(cardTwo, currentBalanceTwoCard - amountCard);

    }

    @Test
    // Должен авторизировать клиента и выполнить перевод не валидной суммы
    @DisplayName("Should authorize user and transfer valid amount money")
    void shouldAuthorizeUserAndTransferInvalidAmountMoney(){

        // Метод генеарции невалидной суммы начисления
        amountCard = DataHelper.generateInvalidAmmount(currentBalanceTwoCard);

        //Выбор карты
        var selectCard = dashBoardPage.selectCard(cardFirst);

        // Перевод средств
        var transferPage = selectCard.transferMoney(cardTwo, amountCard);

        // При переводе невалидной суммы появляется информационное сообщение
        // О том, что сумма перевода не может превышать баланс карты.
            selectCard.erorMassage("Сумма перевода не может превышать баланс карты");

        //Обновление баланса карт
        dashBoardPage.reloadBalance();

        // Проверка соответствия ожидаемых и фактических балансов карт
        dashBoardPage.checkingbalance(cardFirst, currentBalanceFirstCard);
        dashBoardPage.checkingbalance(cardTwo, currentBalanceTwoCard);

    }

}
