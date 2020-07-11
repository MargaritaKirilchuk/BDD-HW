package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }

    public void emptyLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        SelenideElement login = $("[data-test-id=login]");
        login.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public VerificationPage validPassword(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void invalidPassword(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }

    public void emptyPassword(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=action-login]").click();
        SelenideElement password = $("[data-test-id=password]");
        password.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
