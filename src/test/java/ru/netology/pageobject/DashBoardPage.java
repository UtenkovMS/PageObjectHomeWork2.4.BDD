package ru.netology.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

// Информационная страница
public class DashBoardPage {

    // В данной строке указаны два знака $$, таким производится поиск коллекции элементов.
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement reloadBalance = $("[data-test-id='action-reload']");

    // Проверку видимости страницы добавили в конструктор,
    // чтобы данная проверка происходила автоматически каждый раз при загрузке страницы.
    public DashBoardPage() {
        $("div [data-test-id='dashboard']").shouldBe(Condition.visible);
    }

    // Метод поиска карты
    private SelenideElement getCardElement(DataHelper.CardInfo cardInfo) {

        // Condition.attribute производит поиск по атрибуту "data-test-id"
        // Далее вызываем метод .cardInfo из класса DataHelper через переменную cardInfo
        // и получаем из него TestId через команду getTestId()
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    //Метод отображения баланса
    //Для реализации метода нам необходимо найти текст элемента.
    //Для этого вызывается метод getCardElement(), в который передается объект cardInfo
    //И далее через команду .getText() выполняется поиск текста элемента.
    //Результат сохраняется в переменную elementText
    //Далее в строке return extractBalance(elementText) мы вызываем метод extractBalance() в который передаем
    //В качестве аргумента переменную elementText
    //Метод extractBalance(elementText) обрабатывает переменную elementText и возвращает результат в виде целого числа.

    // Метод извлечения баланса, тип переменной задан int - числовой
    public int getCardBalance(DataHelper.CardInfo cardInfo) {

        var elementText = getCardElement(cardInfo).getText();

        return extractBalance(elementText);
    }

    // Метод выбора карты
    // Вызываем метод getCardElement() отвечающий за поиск карты по атрибуду TestId, который описан выше.
    // В данный метод передаем параметр cardInfo.
    // Далее реализуем функцию нажатия кнопки через селектор

    public TransferPage selectCard(DataHelper.CardInfo cardInfo) {

        getCardElement(cardInfo).$("[data-test-id='action-deposit'] span").click();

        return new TransferPage();
    }

    // Метод обновления баланса
    // Метод выполняет нажатие на кнопку обновить
    public DashBoardPage reloadBalance() {

        reloadBalance.click();

        return new DashBoardPage();
    }

    // Метод, который обрабатывает текст, заданный в параметре (String text) и извлекает из него число - баланс карты
    // Метод объявлен приватным
    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    //Метод проверки баланса картьы
    //Сперва находит элемент карты по селектору
    //Во вторых проверяет его видимость
    //В третьих проверяет наличие баланса заданное через параметр
    public void checkingbalance(DataHelper.CardInfo cardInfo, int expectedBalance) {

        getCardElement(cardInfo).shouldBe(Condition.visible).shouldHave(text(balanceStart + expectedBalance + balanceFinish));
    }

}
