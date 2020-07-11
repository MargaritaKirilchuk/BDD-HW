package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class CardsPage {
    public TransferPage CardsForTransfer(String cardNumber) {
        SelenideElement card = $("[data-test-id= \"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
        card.$$("button").find(exactText("Пополнить")).click();
        return new TransferPage();
    }

    public int getCurrentBalance(String cardNumber) {
        SelenideElement card = $("[data-test-id= \"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
        String balance = card.getText().substring(29);
        balance = balance.substring(0,5);
        int currentBalance = Integer.valueOf(balance);
        return currentBalance;
    }

    public int getCurrentBalanceCard2(String cardNumber) {
        SelenideElement card = $("[data-test-id= \"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");
        String balance = card.getText().substring(29);
        balance = balance.substring(0,5);
        int currentBalance = Integer.valueOf(balance);
        return currentBalance;
    }
}
