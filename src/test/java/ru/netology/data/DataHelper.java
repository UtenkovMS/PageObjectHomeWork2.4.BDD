package ru.netology.data;

import lombok.Value;

import java.util.Random;

// Помощник данных - возвращает запрашиваемые данные.
public class DataHelper {

    // Конструктор DataHelper объявлен приватным и пустым.
    // Чтобы нельзя было создать методы класса извне, этот способ обеспечивает безопасность.
    // У конструктора такое же название, как и у класса.
    // Данный конструктор нужен просто пустым для обеспечения безопасности данных.
    private DataHelper() {

    }

    public static Authorization getUserInfo() {

        return new Authorization("vasya", "qwerty123");
    }

    public static Authorization getUserTwoInfo() {

        return new Authorization("kola", "qwerty123");
    }

    // В данном методе в скобках мы указываем, что он принимает аргумент (Authorization user),
    // Т.е. метод может принимать данные о пользователе.
    // Если данный аргумент не указать, то в тесте при вызове метода будет возникать ошибка.
    // Поэтому в тесте, при вызове метода var code = DataHelper.getCode(info),
    // В скобках мы пишем переменную (info), являющейся аргументом.
    // Переменная info в тесте отвечает за вызов метода получения данных о пользователе var info = getUserInfo().

    public static VerificationСode getCode(Authorization user) {

        return new VerificationСode("12345");
    }

    public static VerificationСode getInvalidCode(Authorization user) {

        return new VerificationСode("1234");
    }

    public static CardInfo getFirstCard() {

        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getTwoCard() {

        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static int generateAmount() {

        // Создаем переменную random класса Random, который позволяет использовать методы для генерации случайных чисел
        Random random = new Random();
        // Создаем массив с названиями городов
        int amount[] = {5000, 10000, 10001};
        // создаем переменную rand, которой передаем случайное значение массива city с помощью переменной random и
        // метода .nextInt(city.length)
        int rand = random.nextInt(amount.length);
        return amount[rand];
    }

    @Value
    public static class Authorization {
        String login;
        String password;
    }

    @Value
    public static class VerificationСode {
        String code;
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String testId;
    }

}
