package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountToTransfer = $("[data-test-id=amount] input");
    private SelenideElement cardNumber = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
        amountToTransfer.shouldBe(visible);
    }

    public CardsPage shouldTransferMoneyFromCard1(int amount){
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.card1().getCardNumbers());
        transferButton.click();
        return new CardsPage();
    }

    public CardsPage shouldTransferFromCard2(int amount){
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.card2().getCardNumbers());
        transferButton.click();
        return new CardsPage();
    }

    public CardsPage shouldNotTransferFromCard1EmptyAmount() {
        cardNumber.setValue(DataHelper.Cards.card1().getCardNumbers());
        transferButton.click();
        $("[data-test-id=error-notification]").shouldBe(visible);
        return new CardsPage();
    }

    public CardsPage shouldNotTransferEmptyCardNumber(int amount) {
        amountToTransfer.setValue(Integer.toString(amount));
        transferButton.click();
        $("[data-test-id=error-notification]").shouldBe(visible);
        return new CardsPage();
    }

    public CardsPage shouldNotTransferMoneyFromCard1AmountLessThanBalance(int amount){
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.card1().getCardNumbers());
        transferButton.click();
        $(withText("Недостаточно средств на карте")).waitUntil(Condition.visible, 15000);
        return new CardsPage();
    }

    public CardsPage shouldNotTransferMoneyFromCard2AmountLessThanBalance(int amount) {
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.card2().getCardNumbers());
        transferButton.click();
        $(withText("Недостаточно средств на карте")).waitUntil(Condition.visible, 15000);
        return new CardsPage();
    }

    public void shouldNotTransferInvalidCard (int amount) {
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.invalidCard().getCardNumbers());
        transferButton.click();
        $("[data-test-id=error-notification]").shouldBe(visible);
    }

    public void shouldNotTransferFromCard1ToCard1(int amount) {
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.card1().getCardNumbers());
        transferButton.click();
        $("[data-test-id=error-notification]").shouldBe(visible);
    }

    public void shouldNotTransferFromCard2ToCard2(int amount) {
        amountToTransfer.setValue(Integer.toString(amount));
        cardNumber.setValue(DataHelper.Cards.card2().getCardNumbers());
        transferButton.click();
        $("[data-test-id=error-notification]").shouldBe(visible);
    }
}
